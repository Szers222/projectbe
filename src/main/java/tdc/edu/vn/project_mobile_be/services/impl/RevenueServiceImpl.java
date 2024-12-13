package tdc.edu.vn.project_mobile_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.dtos.responses.revenue.RevenueResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.relationship.ShipmentProduct;
import tdc.edu.vn.project_mobile_be.entities.revenue.Revenue;
import tdc.edu.vn.project_mobile_be.entities.shipment.Shipment;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.RevenueRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ShipmentProductRepository;
import tdc.edu.vn.project_mobile_be.interfaces.reponsitory.ShipmentRepository;
import tdc.edu.vn.project_mobile_be.interfaces.service.RevenueService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RevenueServiceImpl extends AbService<Revenue, UUID> implements RevenueService {
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ShipmentProductRepository shipmentProductRepository;
    @Autowired
    private RevenueRepository revenueRepository;

    public double calculateRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        Timestamp startTimestamp = Timestamp.valueOf(startDate);
        Timestamp endTimestamp = Timestamp.valueOf(endDate);
        List<ShipmentProduct> shipmentProducts = shipmentProductRepository.findByShipmentShipmentDateBetween(startTimestamp, endTimestamp);
        double totalRevenue = 0;

        Map<Shipment, List<ShipmentProduct>> shipmentMap = shipmentProducts.stream()
                .collect(Collectors.groupingBy(ShipmentProduct::getShipment));

        for (Map.Entry<Shipment, List<ShipmentProduct>> entry : shipmentMap.entrySet()) {
            Shipment shipment = entry.getKey();
            List<ShipmentProduct> productsInShipment = entry.getValue();

            double shipmentRevenue = 0;
            for (ShipmentProduct sp : productsInShipment) {
                shipmentRevenue += (sp.getPrice() * 1.1) * sp.getQuantity();
            }

            shipmentRevenue -= shipment.getShipmentShipCost() * (1 - (shipment.getShipmentDiscount() / 100.0));

            totalRevenue += shipmentRevenue;
        }
        return totalRevenue;
    }

    @Override
    public Revenue createRevenue() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
        double calculatedRevenue = calculateRevenue(startOfMonth, now);


        Revenue newRevenue = new Revenue();
        newRevenue.setRevenueId(UUID.randomUUID());
        newRevenue.setRevenueDate(Timestamp.valueOf(now));
        newRevenue.setRevenueTotal(calculatedRevenue);


        return revenueRepository.save(newRevenue);
    }

    @Override
    public List<RevenueResponseDTO> getRevenueByDate(Timestamp from, Timestamp to) {
        List<Revenue> revenues = revenueRepository.findRevenueByDate(from, to);

        List<RevenueResponseDTO> responseDTOs = new ArrayList<>();
        for (Revenue revenue : revenues) {
            RevenueResponseDTO responseDTO = new RevenueResponseDTO();
            responseDTO.setRevenueId(revenue.getRevenueId());
            responseDTO.setDate(revenue.getRevenueDate());
            responseDTO.setRevenue(revenue.getRevenueTotal());

            responseDTOs.add(responseDTO);
        }
        return responseDTOs;
    }
}