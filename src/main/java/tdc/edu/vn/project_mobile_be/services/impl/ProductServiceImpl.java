package tdc.edu.vn.project_mobile_be.services.impl;

import com.sun.net.httpserver.Request;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.repositories.ProductRepository;
import tdc.edu.vn.project_mobile_be.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productReponsitory;


}
