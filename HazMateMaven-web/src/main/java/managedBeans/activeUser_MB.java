/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbMenuFacadeLocal;
import ejb.DbPageFacadeLocal;
import ejb.DbUserFacadeLocal;
import ejb.DbuserPreferencesFacadeLocal;
import entities.DbPage;
import entities.DbUser;
import entities.DbuserPreferences;
import entities.DbuserPreferencesPK;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Charling
 */
@Named(value = "activeUser_MB")
@SessionScoped
public class activeUser_MB implements Serializable {

    @EJB
    private DbuserPreferencesFacadeLocal dbuserPreferencesFacade;

    @EJB
    private DbUserFacadeLocal dbUserFacade;
    private DbUser activeUser = new DbUser();

    @EJB
    private DbMenuFacadeLocal dbMenuFacade;

    @EJB
    private DbPageFacadeLocal dbPageFacade;

    private List<DbPage> userPageList;
    private List<DbPage> completePageList;
    private List<String> restrictedPageList;
    ;
    private List<String> availablePageList;
    private String activeUserName;
    private String activeUserCompany;
    private String activeUserPosition;
    private String activeUserPassword;
    private String currentPassword;
    private List<DbuserPreferencesPK> userPreferencesPK;
    private DbuserPreferencesPK userPreferencePK;
    private List<DbuserPreferences> userPreferences;
    private DbuserPreferences userPreference;

    public List<DbPage> getUserPageList() {
        return userPageList;
    }

    public void setUserPageList(List<DbPage> userPageList) {
        this.userPageList = userPageList;
    }

    public List<DbPage> getCompletePageList() {
        return completePageList;
    }

    public void setCompletePageList(List<DbPage> completePageList) {
        this.completePageList = completePageList;
    }

    public List<String> getRestrictedPageList() {
        return restrictedPageList;
    }

    public void setRestrictedPageList(List<String> restrictedPageList) {
        this.restrictedPageList = restrictedPageList;
    }

    public List<String> getAvailablePageList() {
        return availablePageList;
    }

    public void setAvailablePageList(List<String> availablePageList) {
        this.availablePageList = availablePageList;
    }

    public DbUser getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(DbUser activeUser) {
        this.activeUser = activeUser;
    }

    public String getActiveUserName() {
        return activeUserName;
    }

    public void setActiveUserName(String activeUserName) {
        this.activeUserName = activeUserName;
    }

    public String getActiveUserCompany() {
        return activeUserCompany;
    }

    public void setActiveUserCompany(String activeUserCompany) {
        this.activeUserCompany = activeUserCompany;
    }

    public String getActiveUserPosition() {
        return activeUserPosition;
    }

    public void setActiveUserPosition(String activeUserPosition) {
        this.activeUserPosition = activeUserPosition;
    }

    public String getActiveUserPassword() {
        return activeUserPassword;
    }

    public void setActiveUserPassword(String activeUserPassword) {
        this.activeUserPassword = activeUserPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public List<DbuserPreferences> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<DbuserPreferences> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public DbuserPreferences getUserPreference() {
        return userPreference;
    }

    public void setUserPreference(DbuserPreferences userPreference) {
        this.userPreference = userPreference;
    }

    public List<DbuserPreferencesPK> getUserPreferencesPK() {
        return userPreferencesPK;
    }

    public void setUserPreferencesPK(List<DbuserPreferencesPK> userPreferencesPK) {
        this.userPreferencesPK = userPreferencesPK;
    }

    public DbuserPreferencesPK getUserPreferencePK() {
        return userPreferencePK;
    }

    public void setUserPreferencePK(DbuserPreferencesPK userPreferencePK) {
        this.userPreferencePK = userPreferencePK;
    }

    public activeUser_MB() {
    }

    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        setActiveUserName(activeUser.getFirstName() + " " + activeUser.getLastName());
        setActiveUserCompany(activeUser.getCompany());
        setActiveUserPosition(activeUser.getRoleId().getRoleName());

        setUserPreferences(dbuserPreferencesFacade.getUserPreferences(activeUser.getUserId()));

        // Populate permissions for pages
        completePageList = dbPageFacade.findAll();
        userPageList = dbMenuFacade.customUserMenu(activeUser.getUserId());
        availablePageList = new ArrayList<>();
        restrictedPageList = new ArrayList<>();
        for (DbPage page : userPageList) {
            if (page.getPageLocation().startsWith("/data/")) {
                availablePageList.add(page.getPageName());
            }
        }
        for (DbPage page : completePageList) {
            if (page.getPageLocation().startsWith("/data/")) {
                String pageName = page.getPageName();
                if (!availablePageList.contains(pageName)) {
                    restrictedPageList.add(pageName);
                }
            }
        }
    }

    public String activeChange() {
        if (checkPassword(currentPassword)) {
            String hashedPassword = hashPassword(activeUserPassword);
            activeUser.setPassword(hashedPassword);
            dbUserFacade.edit(activeUser);
            init();
            return "viewUsers";
        } else {
            return "";
        }
    }

    private static final int ITERATIONS = 4096;
    private static final int KEY_LENGTH = 256;
    private static final byte[] SALT = "jcf_5#ecJm%HLyl6".getBytes();

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

    public boolean checkPassword(String currentPassword) {
        String hashedPassword = hashPassword(currentPassword);
        if (hashedPassword.equals(activeUser.getPassword())) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Current password is incorrect!"));
            return false;
        }
    }

}
