/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d20_calculator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 *
 * @author Michael CWJ
 */
@SuppressWarnings("unused")
public class D20_UI_Controller implements Initializable {
    
    @FXML
    private Label label;
    
    /*The text display of the calculator, all eventmethods output to this
    screen.*/
    @FXML
    private TextField txtField;
    
    //Set to true after the equals button is pressed, used to clear the 
    //textfield after the user presses a button (so the numbers don't get
    //appended onto the answer
    private static Boolean isAnswer=false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        //label.setText("Hello World!");
    }
    
    /* Clear button - removes all text from textfield when pressed*/
    @FXML
    private void CbuttonAction(){//(ActionEvent e) <- detect kind of event
        txtField.setText("");
    }
    @FXML
    private void btnDelete(){
        String txt = txtField.getText();
        if(txt.length()>1){
            int index = txt.length()-1;
            //A loop to eat all the digits in a number or die variable (2d20, etc)
            if(index>=0 && Character.isDigit(txt.charAt(index))){
                while(index>=0 && Character.isDigit(txt.charAt(index))){
                    index--;
                    if(index>=0 && txt.charAt(index)=='d'){
                        index--; //skip the 'd' character to get the digits before it
                    }
                }
                index++;
            }
            if(index<0){txtField.setText("");}
            else{
                txt = txt.substring(0, index);
                txtField.setText(txt);
            }
        }else if(txt.length()==1){
            txtField.setText("");
        }
    }
    /*
    Major button, calls the expression evaluator
    */
    @FXML
    private void btnEquals(){
        String expression=txtField.getText();
        try{
            if(!expression.equals("")){
                int result = Evaluator.evaluate(expression);
                txtField.setText(result+"");
                isAnswer=true;
            }            
        }catch(NumberFormatException e){
            txtField.setText("ERROR: Integer too large");
        }catch(Exception e){
            txtField.setText("ERROR");
        }
    }
    private void resetAnswer(){
        if(isAnswer){
            txtField.setText("");
            isAnswer=false;
        }
    }
    
    @FXML private void plus(){
        isAnswer=false;
        txtField.appendText("+");
    }
    @FXML private void minus(){
        isAnswer=false;
        txtField.appendText("-");
    }
    @FXML private void times(){
        isAnswer=false;
        txtField.appendText("*");
    }
    @FXML private void divide(){
        isAnswer=false;
        txtField.appendText("/");
    }
    @FXML private void leftBrace(){
        resetAnswer();
        txtField.appendText("(");
    }
    @FXML private void rightBrace(){
        resetAnswer();
        txtField.appendText(")");
    }
/* Button action listeners */
    @FXML private void btn0(){
        resetAnswer();
        txtField.appendText("0");
    }
    @FXML private void btn1(){
        resetAnswer();
        txtField.appendText("1");
    }
    @FXML private void btn2(){
        resetAnswer();
        txtField.appendText("2");
    }
    @FXML private void btn3(){
        resetAnswer();
        txtField.appendText("3");
    }
    @FXML private void btn4(){
        resetAnswer();
        txtField.appendText("4");
    }
    @FXML private void btn5(){
        resetAnswer();
        txtField.appendText("5");
    }
    @FXML private void btn6(){
        resetAnswer();
        txtField.appendText("6");
    }
    @FXML private void btn7(){
        resetAnswer();
        txtField.appendText("7");
    }
    @FXML private void btn8(){
        resetAnswer();
        txtField.appendText("8");
    }
    @FXML private void btn9(){
        resetAnswer();
        txtField.appendText("9");
    }
    
    @FXML
    private void btndx(){
        resetAnswer();
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d");
        }else{
            txtField.appendText("1d");//makes sure there's a multiple
        }
    }
    @FXML
    private void btnd4(){
        resetAnswer();
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d4");
        }else{
            txtField.appendText("1d4");//makes sure there's a multiple
        }
    }
    @FXML
    private void btnd6(){
        resetAnswer();
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d6");
        }else{
            txtField.appendText("1d6");//makes sure there's a multiple
        }
    }
    @FXML
    private void btnd8(){
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d8");
        }else{
            txtField.appendText("1d8");//makes sure there's a multiple
        }
    }
    @FXML
    private void btnd10(){
        resetAnswer();
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d10");
        }else{
            txtField.appendText("1d10");//makes sure there's a multiple
        }
    }
    @FXML
    private void btnd12(){
        resetAnswer();
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d12");
        }else{
            txtField.appendText("1d12");//makes sure there's a multiple
        }
    }
    @FXML
    private void btnd20(){
        resetAnswer();
        String t=txtField.getText();
        if(t.length()>0 && Character.isDigit(t.charAt(t.length()-1))){
            txtField.appendText("d20");
        }else{
            txtField.appendText("1d20");//makes sure there's a multiple
        }
    }
    /*
    Function for handling certain key presses (using the numpad instead of clicking buttons, for convenience
    tied to the anchor frame via FXML, (used scenebuilder for ease, so not confident with raw editing the
    FXML doc.
    */
    @FXML
    private void keyPressed(KeyEvent e){
        KeyCode k = e.getCode();
        switch(k){
            case NUMPAD0:
                btn0();
                break;
            case NUMPAD1:
                btn1();
                break;
            case NUMPAD2:
                btn2();
                break;
            case NUMPAD3:
                btn3();
                break;
            case NUMPAD4:
                btn4();
                break;
            case NUMPAD5:
                btn5();
                break;
            case NUMPAD6:
                btn6();
                break;
            case NUMPAD7:
                btn7();
                break;
            case NUMPAD8:
                btn8();
                break;
            case NUMPAD9:
                btn9();
                break;
            case ADD:
                plus();
                break;
            case SUBTRACT:
                minus();
                break;
            case MULTIPLY: 
                times();
                break;
            case DIVIDE:
                divide();
                break;
            case EQUALS:
                btnEquals();
                break;
            case ENTER:
                btnEquals();
                break;//*/
            case LEFT_PARENTHESIS:
                leftBrace();
                break;
            case RIGHT_PARENTHESIS:
                rightBrace();
                break;
            case BRACELEFT:
                leftBrace();
                break;
            case BRACERIGHT:
                rightBrace();
                break;
            case BACK_SPACE:
                btnDelete();
                break;
            default:
                break;
        }
    }
}    
