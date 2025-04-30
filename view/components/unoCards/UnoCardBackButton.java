package view.components.unoCards;



import view.components.UnoColor;

public class UnoCardBackButton extends UnoCardButton {

    public UnoCardBackButton() {
        super(UnoColor.BLACK);
        setFaceUp(false); // très important : pour ne pas dessiner le contenu
    }
}
