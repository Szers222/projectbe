package tdc.edu.vn.project_mobile_be.interfaces.service;

import tdc.edu.vn.project_mobile_be.dtos.responses.revenue.RevenueResponseDTO;
import tdc.edu.vn.project_mobile_be.entities.revenue.Revenue;
import tdc.edu.vn.project_mobile_be.interfaces.IService;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface RevenueService extends IService<Revenue, UUID> {

    Revenue createRevenue();

    List<RevenueResponseDTO> getRevenueByDate(Timestamp from, Timestamp to);
}
