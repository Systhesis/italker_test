package com.neil.italker_test.mvptest;

public class UserService implements IUserService{
    @Override
    public String search(int hashcode) {
        return "User:" + hashcode;
    }
}
