package com.m1guelsb.blog.exceptions.handler;

public class HandleConflictException extends RuntimeException {

  static String formatMessage(String ex) {
    String startWord = "Detail:";
    String endWord = ".]";

    int startIndex = ex.indexOf(startWord);
    int endIndex = ex.indexOf(endWord);

    startIndex += startWord.length();
    return ex.substring(startIndex, endIndex).trim();

  }
}