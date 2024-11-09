package tdc.edu.vn.project_mobile_be.interfaces.service;


import org.springframework.stereotype.Service;
import tdc.edu.vn.project_mobile_be.commond.Breadcrumb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BreadcrumbService {
    public List<Breadcrumb> generateProductBreadcrumb(UUID categoryId, UUID productId) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Trang chủ", "/"));
        breadcrumbs.add(new Breadcrumb("Danh mục", "products/categories/" + categoryId));
        breadcrumbs.add(new Breadcrumb("Sản phẩm", "/products/" + "/" + productId));
        return breadcrumbs;
    }

}
