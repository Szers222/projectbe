package tdc.edu.vn.project_mobile_be.commond;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.UUID;

@Component
@Order(0)
public class GuestAuthenticationFilter extends AnonymousAuthenticationFilter {
    public GuestAuthenticationFilter() {
        super("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_GUEST"));
    }

    @Override
    protected Authentication createAuthentication(HttpServletRequest request) {
        UUID guestId = UUID.randomUUID();
        System.console().printf("Request12313" + request.getCookies());
        // Tạo cookie chứa guestId
        Cookie guestIdCookie = new Cookie("guestId", guestId.toString());
        guestIdCookie.setPath("/");
        guestIdCookie.setHttpOnly(true);
        guestIdCookie.setMaxAge(24 * 60 * 60);

        // Thêm cookie vào response
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
        if (response != null) {
            response.addCookie(guestIdCookie);
        }

        return new AnonymousAuthenticationToken("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_GUEST"));
    }


}
