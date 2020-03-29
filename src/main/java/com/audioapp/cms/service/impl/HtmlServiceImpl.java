package com.audioapp.cms.service.impl;

import com.audioapp.cms.service.HtmlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HtmlServiceImpl implements HtmlService {

    @Override
    public String handleHtmlImg(String text) {
        String[] textSplit = text.split("<img");
        for(int cnt = 0; cnt < textSplit.length-1; cnt ++){
            ArrayList<Integer> indexList = new ArrayList<>();
            int index = 0;
            while (text.indexOf("<img",index) != -1){
                index = text.indexOf("<img",index);
                indexList.add(index);
                index++;
            }
            String pText = "";
            String imgString = "";
            int imgStartIndex = 0;
            if(cnt == 0){
                pText = text.substring(0, indexList.get(cnt));
                if(indexList.size() == 1){
                    imgString = text.substring(indexList.get(cnt));
                }else {
                    imgString = text.substring(indexList.get(cnt), indexList.get(cnt + 1));
                }
                imgStartIndex = indexList.get(cnt);
            }else{
                pText = text.substring(indexList.get(cnt-1), indexList.get(cnt));
                if(cnt == indexList.size() - 1) {
                    imgString = text.substring(indexList.get(cnt));
                    imgStartIndex = indexList.get(cnt);
                }else{
                    imgString = text.substring(indexList.get(cnt), indexList.get(cnt + 1));
                    imgStartIndex = indexList.get(cnt);
                }
            }
            //获取P标签的样式属性
            int styleIndex = pText.lastIndexOf("<p");
            int cntTemp = cnt;
            while(styleIndex == -1){
                cntTemp -= 1;
                if(cntTemp == 0){
                    pText = text.substring(0, indexList.get(cntTemp));
                }else{
                    pText = text.substring(indexList.get(cnt-1), indexList.get(cntTemp));
                }
                styleIndex = pText.lastIndexOf("<p");
            }
            String styleText = pText.substring(styleIndex);
            styleIndex = styleText.indexOf("align");
            if(styleIndex < 0) break;//不带样式的跳过
            int pStartIndex = styleIndex + 6;
            int pEndIndex = styleText.indexOf(";",pStartIndex + 1);
            if(styleText.equals("<p align=\"center\">")){
                styleText = "center";
            }else {
                styleText = styleText.substring(pStartIndex + 1, pEndIndex);
            }
            System.out.println("Style: " + styleText);
            imgString = imgString.substring(0, imgString.indexOf(">") + 1);
            int imgEndIndex = imgStartIndex + imgString.indexOf(">") + 1;
            System.out.println("IMG: " + imgString);
            String newString = "<div style=\"text-align: "+styleText+";\">" + imgString + "</div>";
            StringBuffer sb = new StringBuffer();
            sb.append(text.substring(0, imgStartIndex));
            sb.append(newString);
            sb.append(text.substring(imgEndIndex));
            text = sb.toString();
        }
        return text;
    }
}

