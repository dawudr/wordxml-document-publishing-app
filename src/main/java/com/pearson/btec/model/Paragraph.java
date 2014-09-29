package com.pearson.btec.model;

/**
 * Container for all elements within w:p tag
 * Stores the following child elements:
 * w:r -> w:t
 * w:sdt
 */
public class Paragraph {

    private String text;

    public Paragraph(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {

/*
        StringBuilder sb = new StringBuilder();

       for(Object mpObj : paragraphList) {
            HashMap mp = (HashMap) mpObj;
            Iterator it = mp.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                sb.append("<" + pairs.getKey() + ">" + pairs.getValue() + "</" +  pairs.getKey() + ">\r\n");
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        return sb.toString();
    }
    */
/*        StringBuilder sb = new StringBuilder();

        for(String paragraphText : paragraphTextList) {
                sb.append(paragraphText + "\r\n");
        }*/
        return text;
    }

/*    public Element toXML() {
        Element paragraphElement = new Element("paragraph");;
        StringBuilder sb = new StringBuilder();

        for(String paragraphText : paragraphTextList) {
            System.out.println("P=" + paragraphText);

            sb.append(paragraphText);
        }
        paragraphElement.setText(sb.toString());

        return paragraphElement;
    }*/
}
