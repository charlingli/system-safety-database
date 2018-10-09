/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbUserFacadeLocal;
import entities.DbHazard;
import entities.DbUser;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Juan David
 */
@Named(value = "login_MB")
@RequestScoped
public class login_MB implements Serializable {

    @EJB
    private DbUserFacadeLocal dbUserFacade;
    private String idUser;
    private String password;
    private String userEmail;

    private static final int ITERATIONS = 4096;
    private static final int KEY_LENGTH = 256;
    private static final byte[] SALT = "jcf_5#ecJm%HLyl6".getBytes();

    public login_MB() {
    }

    @PostConstruct
    public void init() {
        idUser = "";
        userEmail = "";
        password = "";
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String initSession() {
        String Redirection = null;
        try {
            DbUser loggedUser = dbUserFacade.initSession(userEmail, hashPassword(password));
            if (loggedUser != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("activeUser", loggedUser);
                Redirection = "/admin/masterMenu?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Incorrect User or Password"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Warning", "Technical error"));
        }
        return Redirection;
    }

    public void validateSession() {
        DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        try {
            if (activeUser != null) {
                if (!FacesContext.getCurrentInstance().isPostback()) {
                    String[] ctx = FacesContext.getCurrentInstance().getViewRoot().getViewId().split("\\.");
                    if (ctx.length > 0 && !dbUserFacade.getPageAccessForUser(activeUser.getUserId(), ctx[0]) && !ctx[0].equals("/admin/masterMenu")) {
                        System.out.println("Validation Security, user does not have permission to -> Page: " + ctx[0]);
                        FacesContext.getCurrentInstance().getExternalContext().redirect("./../../admin/privileges.xhtml");
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./../../admin/privileges.xhtml");
            }
        } catch (IOException e) {
            System.out.println("managedBeans.login_MB.validateSession() -> " + e);
        }
    }

    public void closeSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public static String hashPassword(String password) {
        char[] passArray = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(passArray, SALT, ITERATIONS, KEY_LENGTH);
        Arrays.fill(passArray, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(key);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error on hashing password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

}
