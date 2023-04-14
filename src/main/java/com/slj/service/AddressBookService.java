package com.slj.service;

import com.slj.domain.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Mr.shu
* @description 针对表【address_book(地址管理)】的数据库操作Service
* @createDate 2022-11-17 14:20:43
*/
public interface AddressBookService extends IService<AddressBook> {

    void defaultAddress(AddressBook addressBook);

    AddressBook getAddressBook();

    List<AddressBook> getAddressBooks(AddressBook addressBook);
}
