package jeff.baseproject.domain;



import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.util.Calendar;
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
    private boolean isStaff;
    private boolean isActive;
    private Date createDatetime;
    private Date lastLoginDatetime;
    private Date refreshPassword;
    private int invalidLoginCount;

    public User(String name, String email, int permission, boolean isStaff){
        // 이메일 검증
        if(!isValidEmail(email))
        {
          throw new InvalidParameterException();
        }

        this.name = name;

        this.email = email;
        this.permission = permission;
        this.isStaff = isStaff;
        this.isActive = true;

        Date now = new Date();
        this.createDatetime = now;
        this.lastLoginDatetime = now;
        this.invalidLoginCount = 0;
        changePassword(defaultPassword);
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(Date lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public Date getRefreshPassword() {
        return refreshPassword;
    }

    public void setRefreshPassword(Date refreshPassword) {
        this.refreshPassword = refreshPassword;
    }

    public int getInvalidLoginCount() {
        return invalidLoginCount;
    }

    public void setInvalidLoginCount(int invalidLoginCount) {
        this.invalidLoginCount = invalidLoginCount;
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

    /**
     * 비밀번호 검증 메소드
     *
     * @param password 비밀번호 문자열
     * @return 오류 메시지
     */
    public static String isValidPassword(String password) {
        // 최소 8자, 최대 20자 상수 선언
        final int MIN = 8;
        final int MAX = 20;

        // 영어, 숫자, 특수문자 포함한 MIN to MAX 글자 정규식
        final String REGEX =
                "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";
        // 3자리 연속 문자 정규식
        final String SAMEPT = "(\\w)\\1\\1";
        // 공백 문자 정규식
        final String BLANKPT = "(\\s)";

        // 정규식 검사객체
        Matcher matcher;

        // 공백 체크
        if (password == null || "".equals(password)) {
            return "Detected: No Password";
        }

        // ASCII 문자 비교를 위한 UpperCase
        String tmpPw = password.toUpperCase();
        // 문자열 길이
        int strLen = tmpPw.length();

        // 글자 길이 체크
        if (strLen > 20 || strLen < 8) {
            return "Detected: Incorrect Length(Length: " + strLen + ")";
        }

        // 공백 체크
        matcher = Pattern.compile(BLANKPT).matcher(tmpPw);
        if (matcher.find()) {
            return "Detected: Blank";
        }

        // 비밀번호 정규식 체크
        matcher = Pattern.compile(REGEX).matcher(tmpPw);
        if (!matcher.find()) {
            return "Detected: Wrong Regex";
        }

        // 동일한 문자 3개 이상 체크
        matcher = Pattern.compile(SAMEPT).matcher(tmpPw);
        if (matcher.find()) {
            return "Detected: Same Word";
        }

        // 연속된 문자 / 숫자 3개 이상 체크

        // ASCII Char를 담을 배열 선언
        int[] tmpArray = new int[strLen];

        // Make Array
        for (int i = 0; i < strLen; i++) {
            tmpArray[i] = tmpPw.charAt(i);
        }

        // Validation Array
        for (int i = 0; i < strLen - 2; i++) {
            // 첫 글자 A-Z / 0-9
            if ((tmpArray[i] > 47
                    && tmpArray[i + 2] < 58)
                    || (tmpArray[i] > 64
                    && tmpArray[i + 2] < 91)) {
                // 배열의 연속된 수 검사
                // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2
                if (Math.abs(tmpArray[i + 2] - tmpArray[i + 1]) == 1
                        && Math.abs(tmpArray[i + 2] - tmpArray[i]) == 2) {
                    char c1 = (char) tmpArray[i];
                    char c2 = (char) tmpArray[i + 1];
                    char c3 = (char) tmpArray[i + 2];
                    return "Detected: Continuous Pattern: \"" + c1 + c2 + c3 + "\"";
                }
            }
        }
        // Validation Complete
        return ">>> All Pass";

    }

    public String changePassword(String password)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        cal.add(Calendar.MONTH, 6);
        this.refreshPassword = cal.getTime();
        // TODO(Jeff) - 암호화 준비
        String newPassword = defaultPassword;
        return newPassword;
    }
}


