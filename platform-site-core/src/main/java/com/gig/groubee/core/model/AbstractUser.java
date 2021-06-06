package com.gig.groubee.core.model;

import com.gig.groubee.core.types.YNType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Random;

@MappedSuperclass
@Getter @Setter
@DynamicInsert @DynamicUpdate
public abstract class AbstractUser {

    @Column(unique = true)
    protected String username;

    @Column(length = 512)
    protected String password;

    /**
     * 과거 비밀번호 변경 이력
     */
    @Column(length = 512)
    protected String oldPassword1;

    @Column(length = 512)
    protected String oldPassword2;

    @Column(length = 512)
    protected String oldPassword3;

    /**
     * 비밀번호 수정일
     */
    @Column
    protected LocalDateTime passwordModifyAt;

    /**
     * 비밀번호 틀린 횟수
     */
    @Column
    protected int passwordFailCnt = 0;

    /**
     * 비밀번호 틀린 시간
     */
    @Column
    protected LocalDateTime passwordFailTime;

    /**
     * 로그인 IP
     */
    @Column(length = 1024)
    protected String loginIp;


    /**
     * 마지막 로그인 시간
     */
    @Column
    protected LocalDateTime lastLoginAt;

    /**
     * 사용자 명
     */
    @Column
    protected String name;

    /**
     * 휴대폰 번호
     */
    @Column
    private String mobile;

    /**
     * 휴대폰 인증 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YNType mobileAuthYn;

    /**
     * 휴대폰 인증 코드
     */
    @Column(length = 10)
    private String mobileAuthCode;

    /**
     * 휴대폰 인증 만료 시간
     */
    @Column
    private LocalDateTime mobileAuthExpireAt;

    /**
     * 이메일 인증 여부
     */
    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private YNType emailValidYn = YNType.N;

    /**
     * 이메일 인증 코드
     */
    @Column(length = 128)
    private String emailValidCode;

    /**
     * 이메일 인증 시간
     */
    @Column
    private LocalDateTime emailValidTime;

    /**
     * 이메일 인증 expire time
     */
    @Column
    private LocalDateTime emailValidExpireTime;

    /**
     * 활성화 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    protected YNType activeYn = YNType.Y;

    /**
     * 휴면계정 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    protected YNType dormancyYn = YNType.N;

    /**
     * 임시비밀번호 초기화 여부
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    protected YNType passwordResetYn = YNType.N;

    /**
     * 임시비밀번호 만료 시간
     */
    private LocalDateTime passwordResetExpireAt;

    /**
     * 생성자(관리자 username)
     */
    private String createdBy;

    /**
     * 최종 수정자(관리자 username)
     */
    private String lastModifier;

    public abstract Long getId();

    @NotNull
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @Column
    protected LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.createdAt = dateTime;
        this.updatedAt = dateTime;
        this.passwordModifyAt = dateTime;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setPassword(String password, Boolean signupCheck) {
        if (signupCheck) {
            // 최초 회원가입
            this.password = password;
        } else {
            // 기존 회원 패스워드 수정
            if (this.password.equals(oldPassword1) && this.password.equals(oldPassword2) && this.password.equals(oldPassword3)) {
                this.password = password;
                this.oldPassword1 = this.oldPassword2;
                this.oldPassword2 = this.oldPassword3;
                this.oldPassword3 = password;
            } else {

            }
        }
    }

    public boolean generateMobileAuth() {
        int certNumLength = 6;
        Random random = new Random(System.currentTimeMillis());

        int range = (int) Math.pow(10, certNumLength);
        int trim = (int) Math.pow(10, certNumLength - 1);
        int result = random.nextInt(range) + trim;

        if (result > range) {
            result = result - trim;
        }

        this.mobileAuthCode = String.valueOf(result);
        this.mobileAuthExpireAt = LocalDateTime.now().plusMinutes(3);
        this.mobileAuthYn = YNType.N;
        return true;
    }

    public boolean generateEmailAuth() {
        int certNumLength = 6;
        Random random = new Random(System.currentTimeMillis());

        int range = (int) Math.pow(10, certNumLength);
        int trim = (int) Math.pow(10, certNumLength - 1);
        int result = random.nextInt(range) + trim;

        if (result > range) {
            result = result - trim;
        }

        this.emailValidCode = String.valueOf(result);
        this.emailValidExpireTime = LocalDateTime.now().plusMinutes(3);
        this.emailValidYn = YNType.N;
        return true;
    }
}
