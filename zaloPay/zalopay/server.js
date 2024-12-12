const express = require('express');
const app = express();

const axios = require('axios').default; // npm install axios
const CryptoJS = require('crypto-js'); // npm install crypto-js
const moment = require('moment'); // npm install moment
const cors = require('cors');
const bodyParser = require('body-parser');
const qs = require('qs');

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(bodyParser.json());

const config = {
    app_id: "2554",
    key1: "sdngKKJmqEMzvh5QQcdD2A9XBSKUNaYn",
    key2: "trMrHtvjo6myautxDUiAcYsVtaeQ8nhf",
    endpoint: "https://sb-openapi.zalopay.vn/v2/create"
};

app.post("/payment", async (req, res) => {
    const {amount, user, orderId,transID} = req.body
    if (!amount || amount <= 0) {
        return res.status(400).json({ message: "Invalid amount." });
    }

    const embed_data = {
        user_id: user,
        order_id: orderId || `ORDER_${Date.now()}`,
        redirecturl: 'OrderConfirmationScreen'
    };

    const items = [{}];
    const order = {
        app_id: config.app_id,
        app_user: user || "guest",
        app_trans_id: transID,
        app_time: Date.now(),
        item: JSON.stringify(items),
        embed_data: JSON.stringify(embed_data),
        amount: amount,
        description: `Payment for Order #${transID}`,
        callback_url: "https://5282-2405-4802-9154-3a80-c804-4289-b671-2550.ngrok-free.app/callback",
        bank_code: "zalopayapp",
    };

    const data = `${config.app_id}|${order.app_trans_id}|${order.app_user}|${order.amount}|${order.app_time}|${order.embed_data}|${order.item}`;
    order.mac = CryptoJS.HmacSHA256(data, config.key1).toString();
    console.log(order);
    try {
        const response = await axios.post(config.endpoint, null, { params: order });

        return res.status(200).json(response.data);
    } catch (error) {
        console.error("ZaloPay error:", error);
        res.status(500).json({ message: "Internal Server Error" });
    }
});

app.post('/callback', async (req, res) => {
    let result = {};
    console.log(req.body);
    try {
        let dataStr = req.body.data;
        let reqMac = req.body.mac;

        let mac = CryptoJS.HmacSHA256(dataStr, config.key2).toString();
        console.log('mac =', dataStr);

        if (reqMac !== mac) {
            result.return_code = -1;
            result.return_message = 'mac not equal';
        } else {
            let dataJson = JSON.parse(dataStr);

            console.log(
                "Received callback for app_trans_id:",
                dataJson['app_trans_id'],
            );

            // Gọi API kiểm tra trạng thái đơn hàng
            const checkStatusResponse = await axios.post('https://5282-2405-4802-9154-3a80-c804-4289-b671-2550.ngrok-free.app/check-status-order', {
                app_trans_id: dataJson['app_trans_id']
            });

            if (checkStatusResponse.data.return_code === 1) {
                console.log(
                    "update order's status = success where app_trans_id =",
                    dataJson['app_trans_id']
                );
                result.return_code = 1;
                result.return_message = 'success';
            } else {
                console.log('Payment not successful for app_trans_id:', dataJson['app_trans_id']);
                result.return_code = 0;
                result.return_message = 'payment not verified';
            }
        }
    } catch (ex) {
        console.log('Error:::' + ex.message);
        result.return_code = 0;
        result.return_message = ex.message;
    }

    res.json(result);
});



app.post('/check-status-order', async (req, res) => {
    const { app_trans_id } = req.body;
    console.log(app_trans_id)
    let postData = {
        app_id: config.app_id,
        app_trans_id, // Input your app_trans_id
    };

    let data = postData.app_id + '|' + postData.app_trans_id + '|' + config.key1; // appid|app_trans_id|key1
    postData.mac = CryptoJS.HmacSHA256(data, config.key1).toString();

    let postConfig = {
        method: 'post',
        url: 'https://sb-openapi.zalopay.vn/v2/query',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        data: qs.stringify(postData),
    };

    try {
        const result = await axios(postConfig);
        console.log(result.data);
        return res.status(200).json(result.data);
    } catch (error) {
        console.log('lỗi');
        console.log(error);
    }
});

app.listen(3000, () => {
    console.log("Server is running on port 3000");
});
