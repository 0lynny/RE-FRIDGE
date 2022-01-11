package ddwucom.mobile.finalproject.ma01_20191023;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class FoodXMLParser {

    //    xml에서 읽어들일 태그를 구분한 enum  → 정수값 등으로 구분하지 않고 가독성 높은 방식을 사용
    private enum TagType { NONE, GROUP_NAME, DESC_KOR, SERVING_SIZE, NUTR_CONT1, NUTR_CONT2, NUTR_CONT3, NUTR_CONT4,
        NUTR_CONT5, NUTR_CONT6, NUTR_CONT7, NUTR_CONT8, NUTR_CONT9};     // 해당없음, GROUP_NAME, DESC_KOR, SERVING_SIZE, NUTR_CONT1, NUTR_CONT2, NUTR_CONT3, NUTR_CONT4,NUTR_CONT5, NUTR_CONT6, NUTR_CONT7, NUTR_CONT8, NUTR_CONT9

    //    parsing 대상인 tag를 상수로 선언
    private final static String FAULT_RESULT = "faultResult";
    private final static String ITEM_TAG = "row";
    private final static String GROUP_NAME = "GROUP_NAME";
    private final static String DESC_KOR = "DESC_KOR";
    private final static String SERVING_SIZE = "SERVING_SIZE"; //	총내용량
    private final static String NUTR_CONT1 = "NUTR_CONT1";	//열량(kcal)(1회제공량당)
    private final static String NUTR_CONT2 = "NUTR_CONT2";	//탄수화물(g)(1회제공량당)
    private final static String NUTR_CONT3 = "NUTR_CONT3";	//단백질(g)(1회제공량당)
    private final static String NUTR_CONT4 = "NUTR_CONT4";	//지방(g)(1회제공량당)
    private final static String NUTR_CONT5 = "NUTR_CONT5";	//당류(g)(1회제공량당)
    private final static String NUTR_CONT6 = "NUTR_CONT6";	//나트륨(mg)(1회제공량당)
    private final static String NUTR_CONT7 = "NUTR_CONT7";	//콜레스테롤(mg)(1회제공량당)
    private final static String NUTR_CONT8 = "NUTR_CONT8"; //포화지방산(g)(1회제공량당)
    private final static String NUTR_CONT9 = "NUTR_CONT9";	//트랜스지방(g)(1회제공량당)


    private XmlPullParser parser;

    public FoodXMLParser() {
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FoodDTO> parse(String xml) {
        ArrayList<FoodDTO> resultList = new ArrayList<>();
        FoodDTO food = null;
        TagType tagType = TagType.NONE;

        try{
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals(ITEM_TAG)) {
                            food = new FoodDTO();
                        } else if(tag.equals(GROUP_NAME))  {
                            tagType = TagType.GROUP_NAME;
                        } else if(tag.equals(DESC_KOR))  {
                            tagType = TagType.DESC_KOR;
                        } else if(tag.equals(SERVING_SIZE))  {
                            tagType = TagType.SERVING_SIZE;
                        } else if(tag.equals(NUTR_CONT1))  {
                            tagType = TagType.NUTR_CONT1;
                        } else if(tag.equals(NUTR_CONT2))  {
                            tagType = TagType.NUTR_CONT2;
                        } else if(tag.equals(NUTR_CONT3))  {
                            tagType = TagType.NUTR_CONT3;
                        } else if(tag.equals(NUTR_CONT4))  {
                            tagType = TagType.NUTR_CONT4;
                        } else if(tag.equals(NUTR_CONT5))  {
                            tagType = TagType.NUTR_CONT5;
                        } else if(tag.equals(NUTR_CONT6))  {
                            tagType = TagType.NUTR_CONT6;
                        } else if(tag.equals(NUTR_CONT7))  {
                            tagType = TagType.NUTR_CONT7;
                        } else if(tag.equals(NUTR_CONT8))  {
                            tagType = TagType.NUTR_CONT8;
                        } else if(tag.equals(NUTR_CONT9))  {
                            tagType = TagType.NUTR_CONT9;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ITEM_TAG)){
                            resultList.add(food);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType){
                            case GROUP_NAME:
                                food.setGROUP_NAME(parser.getText());
                                break;
                            case DESC_KOR:
                                food.setDESC_KOR(parser.getText());
                                break;
                            case SERVING_SIZE:
                                food.setSERVING_SIZE(parser.getText());
                                break;
                            case NUTR_CONT1:
                                food.setNUTR_CONT1(parser.getText());
                                break;
                            case NUTR_CONT2:
                                food.setNUTR_CONT2(parser.getText());
                                break;
                            case NUTR_CONT3:
                                food.setNUTR_CONT3(parser.getText());
                                break;
                            case NUTR_CONT4:
                                food.setNUTR_CONT4(parser.getText());
                                break;
                            case NUTR_CONT5:
                                food.setNUTR_CONT5(parser.getText());
                                break;
                            case NUTR_CONT6:
                                food.setNUTR_CONT6(parser.getText());
                                break;
                            case NUTR_CONT7:
                                food.setNUTR_CONT7(parser.getText());
                                break;
                            case NUTR_CONT8:
                                food.setNUTR_CONT8(parser.getText());
                                break;
                            case NUTR_CONT9:
                                food.setNUTR_CONT9(parser.getText());
                                break;

                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return resultList;
    }
}
