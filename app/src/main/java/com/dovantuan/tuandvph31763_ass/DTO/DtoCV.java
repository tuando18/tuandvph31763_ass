package com.dovantuan.tuandvph31763_ass.DTO;

public class DtoCV {
    private int id;
    private String name;
    private String content;
    private String status;
    private String start;
    private String end;


    public DtoCV() {
    }

    public DtoCV(String name, String content, String status, String start, String end) {
        this.name = name;
        this.content = content;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public DtoCV(int id, String name, String content, String status, String start, String end) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
