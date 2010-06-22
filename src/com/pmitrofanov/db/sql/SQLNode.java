/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmitrofanov.db.sql;

/**
 *
 * @author pavel
 */
public class SQLNode extends SimpleNode {

  public SQLNode(int i) {
    super(i);
  }

  public SQLNode(SQLParser p, int i) {
    super(p, i);
  }

}
