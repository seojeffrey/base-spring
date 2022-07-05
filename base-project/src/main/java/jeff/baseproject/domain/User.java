package jeff.baseproject.domain;


import java.security.InvalidParameterException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User {
    private static final String defaultPassword = "1@2#Default_PassWord";
    private Long id;
    private String name;
    private String password;
    private String email;
    private int permission;
    private boolean is_staff;
    private boolean is_active;
    private Date create_datetime;
    private Date last_login_datetime;
    private Date refresh_password;
    private int invalid_login_count;

    public User(String name, String email, int permission, boolean is_staff){
        // 이메일 검증
        if(!isValidEmail(email))
        {
          throw new InvalidParameterException();
        }

        this.name = name;
        this.password = defaultPassword;
        this.email = email;
        this.permission = permission;
        this.is_staff = is_staff;
        this.is_active = true;

        Date now = new Date();
        this.create_datetime = now;
        this.last_login_datetime = now;
        this.refresh_password = now;
        this.invalid_login_count = 0;
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public boolean isIs_staff() {
        return is_staff;
    }

    public void setIs_staff(boolean is_staff) {
        this.is_staff = is_staff;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Date getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(Date create_datetime) {
        this.create_datetime = create_datetime;
    }

    public Date getLast_login_datetime() {
        return last_login_datetime;
    }

    public void setLast_login_datetime(Date last_login_datetime) {
        this.last_login_datetime = last_login_datetime;
    }

    public Date getRefresh_password() {
        return refresh_password;
    }

    public void setRefresh_password(Date refresh_password) {
        this.refresh_password = refresh_password;
    }

    public int getInvalid_login_count() {
        return invalid_login_count;
    }

    public void setInvalid_login_count(int invalid_login_count) {
        this.invalid_login_count = invalid_login_count;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


