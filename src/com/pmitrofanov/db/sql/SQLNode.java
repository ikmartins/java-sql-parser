/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmitrofanov.db.sql;

import org.w3c.dom.*;

/**
 *
 * @author pavel
 */
public class SQLNode extends SimpleNode {

  private Document document;
  private Element element;
  private String textValue = "";

  public SQLNode(int i) {
    super(i);
  }

  public SQLNode(SQLParser p, int i) {
    super(p, i);
    document = p.getDocument();
  }

  @Override
  public SQLNode jjtGetChild(int i) {
    return (SQLNode) super.jjtGetChild(i);
  }

  @Override
  public void jjtOpen() {
     super.jjtOpen();
     element = document.createElement(toString());
  }

  @Override
  public void jjtClose() {
     super.jjtClose();
     for (int i = 0; i < jjtGetNumChildren(); i++) {
        element.appendChild(jjtGetChild(i).getElement());
     }
  }

  public Element getElement() {
      return element;
  }

    /**
     * @return the value
     */
    public String getTextValue() {
        return textValue;
    }

    /**
     * @param value the value to set
     */
    public void setTextValue(String text) {
        textValue = text;
        Text textValueNode = document.createTextNode(textValue);
        element.appendChild(textValueNode);
    }

}
