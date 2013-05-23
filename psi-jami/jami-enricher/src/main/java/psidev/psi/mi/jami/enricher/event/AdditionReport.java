package psidev.psi.mi.jami.enricher.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:30
 */
public class AdditionReport{
    private String field;
    private String newValue;

    public AdditionReport(){
    }

    public AdditionReport(String field, String newValue){
        setAdditionValues(field, newValue);
    }


    public void setAdditionValues(String field, String newValue){
        this.field = field;
        this.newValue = newValue;
    }

    public void setField(String field) {this.field = field;}
    public void setNewValue(String newValue) {this.newValue = newValue;}

    public String getField() {return field;}
    public String getNewValue() {return newValue;}
}