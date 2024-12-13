package tdc.edu.vn.project_mobile_be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdc.edu.vn.project_mobile_be.commond.ResponseData;
import tdc.edu.vn.project_mobile_be.entities.revenue.Revenue;
import tdc.edu.vn.project_mobile_be.interfaces.service.RevenueService;

import java.sql.Timestamp;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;

    @PostMapping("/revenue")
    public ResponseEntity<ResponseData<?>> createRevenue() {
        Revenue revenue = revenueService.createRevenue();
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.CREATED, "Revenue created successfully!", revenue);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/revenue")
    public ResponseEntity<ResponseData<?>> getRevenueByDate(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        Timestamp fromTimestamp = Timestamp.valueOf(from.atStartOfDay());
        Timestamp toTimestamp = Timestamp.valueOf(to.atStartOfDay());
        ResponseData<?> responseData = new ResponseData<>(HttpStatus.OK, "Revenue created successfully!", revenueService.getRevenueByDate(fromTimestamp, toTimestamp));
        return ResponseEntity.ok(responseData);
    }
}
