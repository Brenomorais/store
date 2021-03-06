package br.com.breno.store.security;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.breno.store.daos.SecurityDao;
import br.com.breno.store.models.SystemUser;

@Model
public class CurrentUser {

    @Inject
    private HttpServletRequest request;

    @Inject
    private SecurityDao securityDao;

    private SystemUser systemUser;
    
    public boolean hasRole(String role) {
    	return request.isUserInRole(role);
    }
    
    @PostConstruct
    private void loadSystemUser() {

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            String email = request.getUserPrincipal().getName();
            systemUser = securityDao.findByEmail(email);
        }
    }
    
    public SystemUser get() {
        return systemUser;
    }
    
    
    public String logout() {
    	request.getSession().invalidate();
    	return "/admin/livros/lista.xhtml?faces-redirect=true";
    }
}