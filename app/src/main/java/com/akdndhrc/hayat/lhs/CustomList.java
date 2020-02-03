package com.akdndhrc.hayat.lhs;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomList {
    private String headerpos;
    private List<String> listchilditem;
    private List<String> listcount;

    CustomList(String position, List<String> item, List<String> itemcount)
    {
        this.headerpos = position;
        this.listchilditem = item;
        this.listcount = itemcount;
    }

    public CustomList getChild() {
        return new CustomList(headerpos,listchilditem,listcount);

    }
    public String getFirstStringValue() {
        return headerpos;
    }

    public void setFirstStringValue(String headerpos) {
        this.headerpos = headerpos;
    }

    public List<String> getListchilditem() {
        return listchilditem;
    }

    public void setListchilditem(List<String> listchilditem) {
        this.listchilditem = listchilditem;
    }

    public List<String> getListcount() {
        return listcount;
    }

    public void setListcount(List<String> listcount) {
        this.listcount = listcount;
    }

  }