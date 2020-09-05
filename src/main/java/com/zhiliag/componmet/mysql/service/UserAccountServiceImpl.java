package com.zhiliag.componmet.mysql.service;


import com.zhiliag.componmet.bean.BaseResponse;
import com.zhiliag.componmet.mysql.entity.UserAccount;
import com.zhiliag.componmet.mysql.entity.UserAccountRecord;
import com.zhiliag.componmet.mysql.mapper.UserAccountMapper;
import com.zhiliag.componmet.mysql.mapper.UserAccountRecordMapper;
import com.zhiliag.componmet.mysql.vo.UserAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:lizhi
 * @Date: 2020/9/3
 * @des:
 **/
@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class UserAccountServiceImpl implements IUserAccountService {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Autowired
    UserAccountRecordMapper userAccountRecordMapper;

    /**
     * 不加锁的情况下提现操作
     *
     * @param userAccountDto
     */
    @Override
    public void takeMoney(UserAccountDto userAccountDto) {
        try {
            //查询用户账户实体记录
            UserAccount userAccount = userAccountMapper.selectByUserId(userAccountDto.getUserId());
            //判断实体是否存在,以及账户余额是否足够被体现
            if (userAccount != null && userAccount.getAmount().doubleValue() - userAccountDto.getMoney() > 0) {
                //如果最够被体现,则更新账户余额
                userAccountMapper.updateAmount(userAccountDto.getMoney(), userAccount.getUserId());
                //同时记录提现成功时的记录
                UserAccountRecord userAccountRecord = new UserAccountRecord();
                userAccountRecord.setCreateTime(new Date());
                userAccountRecord.setAccountId(userAccount.getId());
                userAccountRecord.setMoney(BigDecimal.valueOf(userAccountDto.getMoney()));
                userAccountRecordMapper.insert(userAccountRecord);
                log.info("未加锁下:当前待提现的金额为:{} 用户账户余额为{}", userAccountDto.getMoney(), userAccount.getAmount());
            }
        }catch (Exception e){
            log.error("数据库异常",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
    }

    /**
     * 加乐观锁的情况下提现操作
     *
     * @param userAccountDto
     */
    @Override
    public BaseResponse takeMoneyWithLock(UserAccountDto userAccountDto) {
        try{
            UserAccount userAccount=userAccountMapper.selectByUserId(userAccountDto.getUserId());
            if (userAccount!=null&&userAccount.getAmount().doubleValue()-userAccountDto.getMoney()>0){
                int res=userAccountMapper.updateAmountByVersion(userAccountDto.getMoney(),userAccountDto.getUserId(),userAccount.getVersion());
                if (res>0){
                    UserAccountRecord userAccountRecord = new UserAccountRecord();
                    userAccountRecord.setCreateTime(new Date());
                    userAccountRecord.setAccountId(userAccount.getId());
                    userAccountRecord.setMoney(BigDecimal.valueOf(userAccountDto.getMoney()));
                    userAccountRecordMapper.insert(userAccountRecord);
                    log.info("加乐观锁下:当前待提现的金额为:{} 用户账户余额为{}", userAccountDto.getMoney(), userAccount.getAmount());
                    return BaseResponse.success(null);
                }
            }else{
                log.error("当前余额为{},不足以提现",userAccount.getAmount());
                return BaseResponse.fail(500,"当前余额为"+userAccount.getAmount()+"不足以体现");
            }
        }catch (Exception e){
            log.error("数据库异常",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return null;
    }

    /**
     * mysql数据库悲观锁
     * select for update
     * @param userAccountDto
     * @return
     */
    @Override
    public BaseResponse takeMoneyWithPesLocking(UserAccountDto userAccountDto) {
        try{
            UserAccount userAccount=userAccountMapper.selectByUserIdWithPesLocking(userAccountDto.getUserId());
            if (userAccount!=null&&userAccount.getAmount().doubleValue()-userAccountDto.getMoney()>0){
                int res=userAccountMapper.updateAmountByPesLocking(userAccountDto.getMoney(),userAccountDto.getUserId());
                if (res>0){
                    UserAccountRecord userAccountRecord = new UserAccountRecord();
                    userAccountRecord.setCreateTime(new Date());
                    userAccountRecord.setAccountId(userAccount.getId());
                    userAccountRecord.setMoney(BigDecimal.valueOf(userAccountDto.getMoney()));
                    userAccountRecordMapper.insert(userAccountRecord);
                    log.info("加悲观锁下:当前待提现的金额为:{} 用户账户余额为{}", userAccountDto.getMoney(), userAccount.getAmount());
                    return BaseResponse.success(null);
                }
            }else{
                log.error("当前余额为{},不足以提现",userAccount.getAmount());
                return BaseResponse.fail(500,"当前余额为"+userAccount.getAmount()+"不足以体现");
            }
        }catch (Exception e){
            log.error("数据库异常",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return null;
    }
}
