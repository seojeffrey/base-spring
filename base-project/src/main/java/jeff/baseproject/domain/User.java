package jeff.baseproject.domain;

import jeff.baseproject.utils.Encrypt;
import jeff.baseproject.utils.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class User {
    private static final String defaultPassword = "1@2#Default_PassWord";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private int permission;
    private boolean isStaff;
    private boolean isActive;
    private LocalDateTime createDatetime;
    private LocalDateTime lastLoginDatetime;
    private LocalDateTime refreshPasswordDatetime;
    private int invalidLoginCount;

    public void Set(String name, String email, String password, int permission, boolean isStaff){
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

        LocalDateTime now = Time.now();
        this.createDatetime = now;
        this.lastLoginDatetime = now;
        this.invalidLoginCount = 0;
        if (password.isEmpty()){
            password = defaultPassword;
        }

        this.password = encryptPassword(password);
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

    public int getInvalidLoginCount() {
        return invalidLoginCount;
    }

    public void setInvalidLoginCount(int invalidLoginCount) {
        this.invalidLoginCount = invalidLoginCount;
    }


    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }
    public LocalDateTime getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(LocalDateTime lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public LocalDateTime getRefreshPasswordDatetime() {
        return refreshPasswordDatetime;
    }

    public void setRefreshPasswordDatetime(LocalDateTime refreshPasswordDatetime) {
        this.refreshPasswordDatetime = refreshPasswordDatetime;
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

    public String encryptPassword(String password)
    {

        this.refreshPasswordDatetime = Time.now().plusMonths(6);
        // TODO(Jeff) - 암호화 준비
        String newPassword = Encrypt.encryptHash(password);
        return newPassword;
    }


}


