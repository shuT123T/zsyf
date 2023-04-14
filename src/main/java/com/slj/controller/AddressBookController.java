package com.slj.controller;

import com.slj.common.BaseContext;
import com.slj.common.Result;
import com.slj.domain.AddressBook;
import com.slj.service.AddressBookService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
@Api(tags = "地址簿管理接口")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public Result<AddressBook> save(@RequestBody AddressBook addressBook) {
        // 获取当前线程中的用户id
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public Result<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        addressBookService.defaultAddress(addressBook);
        return Result.success(addressBook);
    }


    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return Result.success(addressBook);
        } else {
            return Result.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = addressBookService.getAddressBook();

        if (null == addressBook) {
            return Result.error("没有找到该对象");
        } else {
            return Result.success(addressBook);
        }
    }


    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        List<AddressBook> addressBooks = addressBookService.getAddressBooks(addressBook);
        return Result.success(addressBooks);
    }


    /**
     * 修改收获地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public Result<String> updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return Result.success("修改成功");
    }

    /**
     * 删除收货地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> deleteAddress(@RequestParam("ids") List<Long> ids){
        addressBookService.removeByIds(ids);
        return Result.success("删除成功");
    }
}
