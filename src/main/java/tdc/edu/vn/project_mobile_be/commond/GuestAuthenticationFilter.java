package tdc.edu.vn.project_mobile_be.commond;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.UUID;

public class GuestAuthenticationFilter extends AnonymousAuthenticationFilter {
    public GuestAuthenticationFilter() {
        super("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_GUEST"));
    }

    @Override
    protected Authentication createAuthentication(HttpServletRequest request) {
        UUID guestId = UUID.randomUUID();
        request.getSession().setAttribute("guestId", guestId);
        return new AnonymousAuthenticationToken("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_GUEST"));
    }


}
