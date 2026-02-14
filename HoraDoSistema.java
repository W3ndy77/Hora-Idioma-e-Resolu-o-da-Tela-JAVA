
package com.mycompany.horadosistema;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Date;
import java.util.Locale;

public class HoraDoSistema {

    public static void main(String[] args) {
        
        /* Hora do Ssitema */
        Date relogio = new Date();
        System.out.println("A hora do sistema e ");
        System.out.println(relogio.toString());
        
        /* Idioma do Sistema */
        Locale idioma = new Locale("PORTUGUES", "PT");
        System.out.println("Linguagem: " + idioma.getDisplayLanguage());
        
        /* Forma 1 de resolução da tela */
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        System.out.println("Tamanho da largura: " + d.width);
        System.out.println("Tamanho da altura: " + d.height);
        
        
        /* Forma 2 de resolução da tela*/
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int lar = (int) tela.getWidth();
        int alt = (int) tela.getHeight();
        System.out.println("Resolucao da tela: " + lar + "x" + alt);
        
        
    }
}
