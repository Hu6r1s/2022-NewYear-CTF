package com.securityfirst.winterctf.controller.manage;

import com.securityfirst.winterctf.dto.request.manage.AddChallenge;
import com.securityfirst.winterctf.dto.request.manage.AddNotice;
import com.securityfirst.winterctf.dto.request.manage.ModifyChallengeCategory;
import com.securityfirst.winterctf.dto.request.manage.ModifyChallengeDescription;
import com.securityfirst.winterctf.dto.request.manage.ModifyChallengeFlag;
import com.securityfirst.winterctf.dto.request.manage.ModifyChallengeName;
import com.securityfirst.winterctf.dto.request.manage.ModifyChallengeOpen;
import com.securityfirst.winterctf.dto.request.manage.ModifyIsAdmin;
import com.securityfirst.winterctf.dto.request.manage.ModifyIsBan;
import com.securityfirst.winterctf.dto.request.manage.ModifyNick;
import com.securityfirst.winterctf.dto.request.manage.ModifyNotice;
import com.securityfirst.winterctf.dto.request.manage.ModifyPassword;
import com.securityfirst.winterctf.dto.request.manage.ModifyTeam;
import com.securityfirst.winterctf.dto.request.manage.ModifyUserId;
import com.securityfirst.winterctf.dto.response.manage.AddChallengeFileResponse;
import com.securityfirst.winterctf.dto.response.manage.AddChallengeResponse;
import com.securityfirst.winterctf.dto.response.manage.AddNoticeResponse;
import com.securityfirst.winterctf.dto.response.manage.AllLogCountResponse;
import com.securityfirst.winterctf.dto.response.manage.ChallengeFileDownloadLogResponse;
import com.securityfirst.winterctf.dto.response.manage.FlagLogResponse;
import com.securityfirst.winterctf.dto.response.manage.LoginLogResponse;
import com.securityfirst.winterctf.dto.response.manage.ChallengeByAdminResponse;
import com.securityfirst.winterctf.dto.response.manage.ChallengeListByAdminResponse;
import com.securityfirst.winterctf.dto.response.manage.LogCountResponse;
import com.securityfirst.winterctf.dto.response.manage.ModifyResponse;
import com.securityfirst.winterctf.dto.response.manage.UserInfoResponse;
import com.securityfirst.winterctf.dto.response.manage.UserListResponse;
import com.securityfirst.winterctf.entity.Challenge;
import com.securityfirst.winterctf.mapper.ChallengeListByAdminMapper;
import com.securityfirst.winterctf.mapper.UserInfoMapper;
import com.securityfirst.winterctf.mapper.UserListMapper;
import com.securityfirst.winterctf.security.UserInfoPrincipal;
import com.securityfirst.winterctf.service.AuthService;
import com.securityfirst.winterctf.service.ChallengeService;
import com.securityfirst.winterctf.service.LogService;
import com.securityfirst.winterctf.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/manage")
@Validated
public class ManageController {

  @Autowired
  AuthService authService;
  @Autowired
  ChallengeService challengeService;
  @Autowired
  NoticeService noticeService;
  @Autowired
  LogService logService;

  @GetMapping("/user")
  public UserListResponse getUserListByAdmin() {
    List<UserListMapper> userList = authService.getUserListByAdmin();
    return new UserListResponse(true, userList);
  }

  @GetMapping("/user/{id}")
  public UserInfoResponse getUserInfoByUserId(
      @PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id) {
    UserInfoMapper userInfoMapper = authService.getUserInfoById(Long.parseLong(id));
    return new UserInfoResponse(true, userInfoMapper);
  }

  @PostMapping("/user/id/{id}")
  public ModifyResponse modifyUserId(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id, @RequestBody
      ModifyUserId modifyUserId) {
    boolean result = authService.modifyUserId(Long.parseLong(id), modifyUserId.getUserId());
    return new ModifyResponse(result);

  }

  @PostMapping("/user/nick/{id}")
  public ModifyResponse modifyNick(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id, @RequestBody
      ModifyNick modifyNick) {
    boolean result = authService.modifyNick(Long.parseLong(id), modifyNick.getNick());
    return new ModifyResponse(result);
  }

  @PostMapping("/user/password/{id}")
  public ModifyResponse modifyPassword(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody
          ModifyPassword modifyPassword) {
    boolean result = authService.modifyPassword(Long.parseLong(id), modifyPassword.getPassword());
    return new ModifyResponse(result);
  }

  @PostMapping("/user/ban/{id}")
  public ModifyResponse modifyIsBan(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id, @RequestBody
      ModifyIsBan modifyIsBan) {
    boolean result = authService.modifyIsBan(Long.parseLong(id), modifyIsBan.isBan());
    return new ModifyResponse(result);
  }

  @PostMapping("/user/admin/{id}")
  public ModifyResponse modifyIsAdmin(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody
          ModifyIsAdmin modifyIsAdmin) {
    boolean result = authService.modifyIsAdmin(Long.parseLong(id), modifyIsAdmin.isAdmin());
    return new ModifyResponse(result);
  }

  @PostMapping("/user/team/{id}")
  public ModifyResponse modifyIsAdmin(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody
          ModifyTeam modifyTeam) {
    boolean result = authService.modifyTeam(Long.parseLong(id), modifyTeam.getTeam());
    return new ModifyResponse(result);
  }

  @GetMapping("/challenge/category/{category}")
  public ChallengeListByAdminResponse getChallengeListByAdmin(@PathVariable("category") @NotBlank String category) {
    List<ChallengeListByAdminMapper> challengeList = challengeService.getChallengeListByAdmin(category);
    return new ChallengeListByAdminResponse(true, challengeList);
  }

  @GetMapping("/challenge/{id}")
  public ChallengeByAdminResponse getChallengeInfoByAdmin(
      @PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id) throws Exception {
    Challenge challenge = challengeService.getChallengeByAdmin(Long.parseLong(id));
    return new ChallengeByAdminResponse(true, challenge);
  }

  @GetMapping("/challenge/dynamic/{id}")
  public ModifyResponse dynamicScoring(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id){
    challengeService.dynamicScoring(Long.parseLong(id));
    return new ModifyResponse(true);
  }

  @PostMapping("/challenge")
  public AddChallengeResponse addChallenge(@RequestBody AddChallenge addChallenge,
      @AuthenticationPrincipal UserInfoPrincipal userInfoPrincipal) {
    Long id = challengeService.addChallenge(addChallenge, userInfoPrincipal);
    return new AddChallengeResponse(true, id);
  }

  @PostMapping("/challenge/file/{id}")
  public AddChallengeFileResponse addChallengeFile(
      @PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id, @RequestParam("file")
      MultipartFile multipartFile) throws IOException {
    boolean uploadResult = challengeService.addChallengeFile(Long.parseLong(id), multipartFile);
    return new AddChallengeFileResponse(uploadResult);
  }

  @PostMapping("/challenge/name/{id}")
  public ModifyResponse modifyChallengeName(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody ModifyChallengeName modifyChallengeName) {
    boolean result = challengeService.modifyChallengeName(Long.parseLong(id), modifyChallengeName.getChallengeName());
    return new ModifyResponse(result);
  }

  @PostMapping("/challenge/category/{id}")
  public ModifyResponse modifyChallengeCategory(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody ModifyChallengeCategory modifyChallengeCategory) {
    boolean result = challengeService
        .modifyChallengeCategory(Long.parseLong(id), modifyChallengeCategory.getChallengeCategory());
    return new ModifyResponse(result);
  }

  @PostMapping("/challenge/description/{id}")
  public ModifyResponse modifyChallengeDescription(
      @PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody ModifyChallengeDescription modifyChallengeDescription) {
    boolean result = challengeService
        .modifyChallengeDescription(Long.parseLong(id), modifyChallengeDescription.getChallengeDescription());
    return new ModifyResponse(result);
  }

  @PostMapping("/challenge/flag/{id}")
  public ModifyResponse modifyChallengeFlag(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody ModifyChallengeFlag modifyChallengeFlag) {
    boolean result = challengeService.modifyChallengeFlag(Long.parseLong(id), modifyChallengeFlag.getChallengeFlag());
    return new ModifyResponse(result);
  }

  @PostMapping("/challenge/open/{id}")
  public ModifyResponse modifyChallengeOpen(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id,
      @RequestBody ModifyChallengeOpen modifyChallengeOpen) {
    boolean result = challengeService.modifyChallengeOpen(Long.parseLong(id), modifyChallengeOpen.isChallengeOpen());
    return new ModifyResponse(result);
  }

  @DeleteMapping("/challenge/{id}")
  public ModifyResponse deleteChallenge(@PathVariable("id") @NotBlank @Pattern(regexp = "^[0-9]*$") String id) {
    challengeService.deleteChallenge(Long.parseLong(id));
    return new ModifyResponse(true);
  }

  @PostMapping("/notice")
  public AddNoticeResponse addNotice(@RequestBody AddNotice addNotice) {
    noticeService.addNotice(addNotice);
    return new AddNoticeResponse(true);
  }

  @PostMapping("/notice/{id}")
  public ModifyResponse modifyNoticeTitle(@PathVariable("id") String id, @RequestBody ModifyNotice modifyNotice) {
    noticeService.modifyNotice(Long.parseLong(id), modifyNotice);
    return new ModifyResponse(true);
  }

  @DeleteMapping("/notice/{id}")
  public ModifyResponse deleteNotice(@PathVariable("id") String id) {
    noticeService.deleteNotice(Long.parseLong(id));
    return new ModifyResponse(true);
  }

  @GetMapping("/log/count/login")
  public AllLogCountResponse getAllLoginLogCount() {
    return new AllLogCountResponse(true, logService.getLoginLogAllCount());
  }

  @GetMapping("/log/count/flag")
  public AllLogCountResponse getAllFlagLogCount() {
    return new AllLogCountResponse(true, logService.getFlagLogAllCount());
  }

  @GetMapping("/log/count/download")
  public AllLogCountResponse getAllChallengeFileLogCount() {
    return new AllLogCountResponse(true, logService.getChallengeFileDownloadLogAllCount());
  }

  @GetMapping("/log/login/{page}")
  public LoginLogResponse getAllLoginLog(@PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new LoginLogResponse(true, logService.getLoginLogAll(pageNumber));
  }

  @GetMapping("/log/flag/{page}")
  public FlagLogResponse getAllFlagLog(@PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new FlagLogResponse(true, logService.getFlagLogAll(pageNumber));
  }

  @GetMapping("/log/download/{page}")
  public ChallengeFileDownloadLogResponse getAllChallengeFileDownloadLog(@PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new ChallengeFileDownloadLogResponse(true, logService.getChallengeFileDownloadLogAll(pageNumber));
  }

  @GetMapping("/log/count/login/nick/{nick}")
  public LogCountResponse getLoginLogCountByNick(@PathVariable("nick") String nick) {
    return new LogCountResponse(true, logService.getLoginLogCountByNick(nick));
  }

  @GetMapping("/log/count/login/ip/{ip}")
  public LogCountResponse getLoginLogCountByIp(@PathVariable("ip") String ip) {
    return new LogCountResponse(true, logService.getLoginLogCountByIp(ip));
  }

  @GetMapping("/log/count/flag/nick/{nick}")
  public LogCountResponse getFlagLogCountByNick(@PathVariable("nick") String nick) {
    return new LogCountResponse(true, logService.getFlagLogCountByNick(nick));
  }

  @GetMapping("/log/count/flag/id/{id}")
  public LogCountResponse getFlagLogCountByChallengeId(@PathVariable("id") String id) {
    return new LogCountResponse(true, logService.getFlagLogCountByChallengeId(Long.parseLong(id)));
  }

  @GetMapping("/log/count/download/nick/{nick}")
  public LogCountResponse getChallengeFileDownloadLogCountByNick(@PathVariable("nick") String nick) {
    return new LogCountResponse(true, logService.getChallengeFileDownloadLogCountByNick(nick));
  }

  @GetMapping("/log/count/download/id/{id}")
  public LogCountResponse getChallengeFileDownloadByChallengeId(@PathVariable("id") String id) {
    return new LogCountResponse(true, logService.getChallengeFileDownloadByChallengeId(Long.parseLong(id)));
  }

  @GetMapping("/log/login/nick/{nick}/{page}")
  public LoginLogResponse getLoginLogByNick(@PathVariable("nick") String nick, @PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new LoginLogResponse(true, logService.getLoginLogByNick(nick, pageNumber));
  }

  @GetMapping("/log/login/ip/{ip}/{page}")
  public LoginLogResponse getLoginLogByIp(@PathVariable("page") String page, @PathVariable("ip") String ip) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new LoginLogResponse(true, logService.getLoginLogByIp(ip, pageNumber));
  }

  @GetMapping("/log/flag/nick/{nick}/{page}")
  public FlagLogResponse getFlagLogByNick(@PathVariable("nick") String nick, @PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new FlagLogResponse(true, logService.getFlagLogByNick(nick, pageNumber));
  }

  @GetMapping("/log/flag/id/{id}/{page}")
  public FlagLogResponse getFlagLogByChallengeId(@PathVariable("id") String id, @PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new FlagLogResponse(true, logService.getFlagLogByChallengeId(Long.parseLong(id), pageNumber));
  }

  @GetMapping("/log/download/nick/{nick}/{page}")
  public ChallengeFileDownloadLogResponse getChallengeFileDownloadByNick(@PathVariable("nick") String nick, @PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new ChallengeFileDownloadLogResponse(true, logService.getChallengeFileDownloadByNick(nick, pageNumber));
  }

  @GetMapping("/log/download/id/{id}/{page}")
  public ChallengeFileDownloadLogResponse getChallengeFileDownloadChallengeId(@PathVariable("id") String id, @PathVariable("page") String page) {
    int pageNumber = ((Integer.parseInt(page) - 1) * 10);
    return new ChallengeFileDownloadLogResponse(true, logService.getChallengeFileDownloadByChallengeId(Long.parseLong(id), pageNumber));
  }
}
