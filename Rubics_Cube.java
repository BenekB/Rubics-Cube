//  author: Justyna Jelinska
//  author: Benedykt Bela

package KostkaRubika;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Stack;



public class KostkaRubika extends JFrame
{
    //wykorzystywane do zmiany polozenia obiektow w czasie dzialania programu
    private RotateBehavior awtBehavior_1;
    private RotateBehavior awtBehavior_2;
    private RotateBehavior awtBehavior_3;
    private RotateBehavior awtBehavior_4;
    private RotateBehavior awtBehavior_5;
    private RotateBehavior awtBehavior_6;
    private RotateBehavior awtBehavior_7;
    private RotateBehavior awtBehavior_8;
    private RotateBehavior awtBehavior_9;
    private RotateBehavior awtBehavior_10;
    private RotateBehavior awtBehavior_11;
    private RotateBehavior awtBehavior_12;
    private RotateBehavior awtBehavior_13;
    private RotateBehavior awtBehavior_14;
    private RotateBehavior awtBehavior_15;
    private RotateBehavior awtBehavior_16;
    private RotateBehavior awtBehavior_17;
    private RotateBehavior awtBehavior_18;
    private RotateBehavior awtBehavior_19;
    private RotateBehavior awtBehavior_20;
    private RotateBehavior awtBehavior_21;
    private RotateBehavior awtBehavior_22;
    private RotateBehavior awtBehavior_23;
    private RotateBehavior awtBehavior_24;
    private RotateBehavior awtBehavior_25;
    private RotateBehavior awtBehavior_26;
    private RotateBehavior_indicator indicator_behavior;
    
    private JButton instrukcja;
    private JButton cofnij;
    private JButton od_nowa;
    private JPanel kontener;
    
    ActionListener buttons;
    
//    stos, ktory przechowuje wykonane ruchy, aby mozna bylo je cofac
    Stack stos_cofnij = new Stack();
    
//    instancja klasy, ktora jest wlasciwie glowna klasa powodujaca obroty scian kostki
    Tablica_obrotow tablica_obrotow = new Tablica_obrotow();
    
    
//    stale, ktore zwiekszaja czytelnosc programu i ulatwiaja pisanie
    final int OBROT_Y1_RIGHT = 1;
    final int OBROT_Y1_LEFT = 2;
    final int OBROT_Y2_RIGHT = 3;
    final int OBROT_Y2_LEFT = 4;
    final int OBROT_Y3_RIGHT = 5;
    final int OBROT_Y3_LEFT = 6;
    final int OBROT_X1_RIGHT = 7;
    final int OBROT_X1_LEFT = 8;
    final int OBROT_X2_RIGHT = 9;
    final int OBROT_X2_LEFT = 10;
    final int OBROT_X3_RIGHT = 11;
    final int OBROT_X3_LEFT = 12;
    final int OBROT_Z1_RIGHT = 13;
    final int OBROT_Z1_LEFT = 14;
    final int OBROT_Z2_RIGHT = 15;
    final int OBROT_Z2_LEFT = 16;
    final int OBROT_Z3_RIGHT = 17;
    final int OBROT_Z3_LEFT = 18;
    
    boolean czy_cofac = true;
    
    
    
    KostkaRubika()
    {
        super("Kostka Rubika");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

//        wstawianie przycisków do okna programu i ustawianie ich wyglądu
        kontener = new JPanel();
        kontener.setSize(1000, 50);
        kontener.setLocation(0, 750);
        kontener.setVisible(true);
        kontener.setLayout(null);
        
        instrukcja = new JButton("INSTRUKCJA");
        instrukcja.setSize(200, 50);
        instrukcja.setLocation(100, 0);
        instrukcja.setVisible(true);
        instrukcja.addActionListener(new Button_action(this));
        
        cofnij = new JButton("COFNIJ");
        cofnij.setSize(200, 50);
        cofnij.setLocation(400, 0);
        cofnij.setVisible(true);
        cofnij.addActionListener(new Button_action(this));
        
        od_nowa = new JButton("OD NOWA");
        od_nowa.setSize(200, 50);
        od_nowa.setLocation(700, 0);
        od_nowa.setVisible(true);
        od_nowa.addActionListener(new Button_action(this));
        
        kontener.add(instrukcja);
        kontener.add(cofnij);
        kontener.add(od_nowa);
        
        this.add(kontener);
    
        
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(1000,800));

        add(canvas3D);
        pack();
        setVisible(true);

        BranchGroup scena = utworzScene();
        scena.compile();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

//        obracanie widokiem
        simpleU.getViewingPlatform().setNominalViewingTransform();
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        simpleU.getViewingPlatform().setViewPlatformBehavior(orbit);
        
//        wstępne przesunięcie obserwatora na starcie programu
        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,4.0f));
        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

        simpleU.addBranchGraph(scena);
        
        canvas3D.addKeyListener(klawiatura);
        
    }
    
    
    
//    obsługa przycisków
    private class Button_action implements ActionListener
    {
        private JFrame okno;
        private int do_cofania = 0;
        
        
        Button_action (JFrame kostka)
        {
            okno = kostka;
        }
        
        
       public void actionPerformed(ActionEvent e)
       {
//            jeżeli został naciśnięty przycisk "instrukcja"
            if (instrukcja == (JButton)e.getSource())
                new Instrukcja();
//            jeżeli stos do cofania nie jest pusty i został wciśnięty przycisk "cofnij"
            else if (!stos_cofnij.empty() && cofnij == (JButton)e.getSource())
            {
                
//                do zmiennej do_cofania przypisuję wartość ze szczytu stosu
                do_cofania = (int)stos_cofnij.pop();
                
//                zmienna potrzebna, abyśmy nie wpadli w błędne koło - to jest jeżeli cofamy ruch, to 
//                żeby nie zapisywać tego ruchu cofającego na szczyt stosu
                czy_cofac = false;
                
//                w zależności od zmiennej do_cofania wykonujemy odpowiedni ruch kostki
                switch (do_cofania)
                {
                    case OBROT_X1_LEFT: tablica_obrotow.obrot_x1(true);     break;
                    case OBROT_X2_LEFT: tablica_obrotow.obrot_x2(true);     break;
                    case OBROT_X3_LEFT: tablica_obrotow.obrot_x3(true);     break;
                    case OBROT_X1_RIGHT: tablica_obrotow.obrot_x1(false);     break;
                    case OBROT_X2_RIGHT: tablica_obrotow.obrot_x2(false);     break;
                    case OBROT_X3_RIGHT: tablica_obrotow.obrot_x3(false);     break;
                    case OBROT_Y1_LEFT: tablica_obrotow.obrot_y1(true);     break;
                    case OBROT_Y2_LEFT: tablica_obrotow.obrot_y2(true);     break;
                    case OBROT_Y3_LEFT: tablica_obrotow.obrot_y3(true);     break;
                    case OBROT_Y1_RIGHT: tablica_obrotow.obrot_y1(false);     break;
                    case OBROT_Y2_RIGHT: tablica_obrotow.obrot_y2(false);     break;
                    case OBROT_Y3_RIGHT: tablica_obrotow.obrot_y3(false);     break;
                    case OBROT_Z1_LEFT: tablica_obrotow.obrot_z1(true);     break;
                    case OBROT_Z2_LEFT: tablica_obrotow.obrot_z2(true);     break;
                    case OBROT_Z3_LEFT: tablica_obrotow.obrot_z3(true);     break;
                    case OBROT_Z1_RIGHT: tablica_obrotow.obrot_z1(false);     break;
                    case OBROT_Z2_RIGHT: tablica_obrotow.obrot_z2(false);     break;
                    case OBROT_Z3_RIGHT: tablica_obrotow.obrot_z3(false);     break;
                    
                }
            }
//            jeżeli naciśniemy przycisk "od nowa", to cofamy wszystkie ruchy aż do samego początku
            else if (od_nowa == (JButton)e.getSource())
            {
                int a = stos_cofnij.size();
                for (int i = 0; i < a; i++)
                    cofnij.doClick(4);
            }
       }
    }
    
    
//    obsługa klawiatury
    KeyListener klawiatura = new KeyListener() 
    {
        
//        stałe i jedna zmienna wykorzystywane do sterowania kostką
        int ktora_plaszczyzna = 2;
        private final int X1 = 1;
        private final int X2 = 2;
        private final int X3 = 3;
        private final int Y1 = 4;
        private final int Y2 = 5;
        private final int Y3 = 6;
        private final int Z1 = 7;
        private final int Z2 = 8;
        private final int Z3 = 9;
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }

//        obecny system jest tak skonstruowany, że wybierając cyfry od 1 do 9 z klawiatury
//        wybieramy którą ścianą chcemy obracać i za pomocą strzałek na klawiaturze nią obracamy
//        drugą opcją jest zamiana płaszczyzny (prostopadle do osi x, osi y lub osi z) za pomocą 
//        klawisza S oraz przesuwanie tej płaszczyzny wzdłuż osi prostopadłej do płaszczyzny
//        za pomocą klawisza D
        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_S)
            {
                switch (ktora_plaszczyzna)
                {
                    case X1:
                    case X2:
                    case X3:    ktora_plaszczyzna = Y2; 
                                indicator_behavior.y2();
                                break;
                    case Y1:
                    case Y2:
                    case Y3:    ktora_plaszczyzna = Z2; 
                                indicator_behavior.z2();
                                break;    
                    case Z1:
                    case Z2:
                    case Z3:    ktora_plaszczyzna = X2; 
                                indicator_behavior.x2();
                                break;                                
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_D)
            {
                switch (ktora_plaszczyzna)
                {
                    case X1:    ktora_plaszczyzna = X2; 
                                indicator_behavior.x2();
                                break;
                    case X2:    ktora_plaszczyzna = X3; 
                                indicator_behavior.x3();
                                break;
                    case X3:    ktora_plaszczyzna = X1; 
                                indicator_behavior.x1();
                                break;
                    case Y1:    ktora_plaszczyzna = Y2; 
                                indicator_behavior.y2();
                                break;
                    case Y2:    ktora_plaszczyzna = Y3; 
                                indicator_behavior.y3();
                                break;
                    case Y3:    ktora_plaszczyzna = Y1; 
                                indicator_behavior.y1();
                                break;
                    case Z1:    ktora_plaszczyzna = Z2; 
                                indicator_behavior.z2();
                                break;
                    case Z2:    ktora_plaszczyzna = Z3; 
                                indicator_behavior.z3();
                                break;
                    case Z3:    ktora_plaszczyzna = Z1; 
                                indicator_behavior.z1();
                                break;                          
                }
            }
            else if (e.getKeyCode() == KeyEvent.VK_1)
            {
                System.out.print("1_pressed");
                ktora_plaszczyzna = X1;
                indicator_behavior.x1();
            }
            else if (e.getKeyCode() == KeyEvent.VK_2)
            {
                System.out.print("2_pressed");
                ktora_plaszczyzna = X2;
                indicator_behavior.x2();
            }
            else if (e.getKeyCode() == KeyEvent.VK_3)
            {
                System.out.print("3_pressed");
                ktora_plaszczyzna = X3;
                indicator_behavior.x3();
            }
            else if (e.getKeyCode() == KeyEvent.VK_4)
            {
                System.out.print("4_pressed");
                ktora_plaszczyzna = Y1;
                indicator_behavior.y1();
            }
            else if (e.getKeyCode() == KeyEvent.VK_5)
            {
                System.out.print("5_pressed");
                ktora_plaszczyzna = Y2;
                indicator_behavior.y2();
            }
            else if (e.getKeyCode() == KeyEvent.VK_6)
            {
                System.out.print("6_pressed");
                ktora_plaszczyzna = Y3;
                indicator_behavior.y3();
            } 
            else if (e.getKeyCode() == KeyEvent.VK_7)
            {
                System.out.print("7_pressed");
                ktora_plaszczyzna = Z1;
                indicator_behavior.z1();
            }
            else if (e.getKeyCode() == KeyEvent.VK_8)
            {
                System.out.print("8_pressed");
                ktora_plaszczyzna = Z2;
                indicator_behavior.z2();
            }
            else if (e.getKeyCode() == KeyEvent.VK_9)
            {
                System.out.print("9_pressed");
                ktora_plaszczyzna = Z3;
                indicator_behavior.z3();
            }
//            no i tutaj w zależności od tego która płaszczyzna została wybrana wykonuję obrót 
//            wokół odpowiedniej osi
            else if (e.getKeyCode() == KeyEvent.VK_UP)
            {
                System.out.print("up_pressed");

                switch (ktora_plaszczyzna)
                {
                    case X1:    tablica_obrotow.obrot_x1(true);     break;
                    case X2:    tablica_obrotow.obrot_x2(true);     break;
                    case X3:    tablica_obrotow.obrot_x3(true);     break;
                    case Y1:    tablica_obrotow.obrot_y1(true);     break;
                    case Y2:    tablica_obrotow.obrot_y2(true);     break;
                    case Y3:    tablica_obrotow.obrot_y3(true);     break;
                    case Z1:    tablica_obrotow.obrot_z1(true);     break;
                    case Z2:    tablica_obrotow.obrot_z2(true);     break;
                    case Z3:    tablica_obrotow.obrot_z3(true);     break;
                }  
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                System.out.print("down_pressed");
                
                switch (ktora_plaszczyzna)
                {
                    case X1:    tablica_obrotow.obrot_x1(false);     break;
                    case X2:    tablica_obrotow.obrot_x2(false);     break;
                    case X3:    tablica_obrotow.obrot_x3(false);     break;
                    case Y1:    tablica_obrotow.obrot_y1(false);     break;
                    case Y2:    tablica_obrotow.obrot_y2(false);     break;
                    case Y3:    tablica_obrotow.obrot_y3(false);     break;
                    case Z1:    tablica_obrotow.obrot_z1(false);     break;
                    case Z2:    tablica_obrotow.obrot_z2(false);     break;
                    case Z3:    tablica_obrotow.obrot_z3(false);     break;
                } 
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    };
             
//    główny konstruktor całej sceny
    BranchGroup utworzScene(){

        BranchGroup scena = new BranchGroup();
        scena.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        scena.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
      
      
        TransformGroup wezel_scena = new TransformGroup();
        wezel_scena.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        wezel_scena.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

      
//        poniższa zmienna oznacza szerokość boku pojedynczej kosteczki
//        dla bok_kostki = 0.25f kostki stykają się ze sobą
        float bok_kostki = 0.24f; 
      
        
//        poniższe linijki tworzą poszczególne kosteczki oraz ustawiają ich przesunięcia
        ColorCube kostka_1 = new ColorCube(bok_kostki);
        Transform3D przesun_1 = new Transform3D();
        przesun_1.setTranslation(new Vector3f(0.5f,0.5f,0.0f));
        TransformGroup kostka_trans_1 = new TransformGroup(przesun_1);
        kostka_trans_1.addChild(kostka_1);
      
        ColorCube kostka_2 = new ColorCube(bok_kostki);
        Transform3D przesun_2 = new Transform3D();
        przesun_2.setTranslation(new Vector3f(0.5f,0.0f,0.0f));
        TransformGroup kostka_trans_2 = new TransformGroup(przesun_2);
        kostka_trans_2.addChild(kostka_2);
      
        ColorCube kostka_3 = new ColorCube(bok_kostki);
        Transform3D przesun_3 = new Transform3D();
        przesun_3.setTranslation(new Vector3f(0.5f,-0.5f,0.0f));
        TransformGroup kostka_trans_3 = new TransformGroup(przesun_3);
        kostka_trans_3.addChild(kostka_3);

        ColorCube kostka_4 = new ColorCube(bok_kostki);
        Transform3D przesun_4 = new Transform3D();
        przesun_4.setTranslation(new Vector3f(0.5f,-0.5f,-0.5f));
        TransformGroup kostka_trans_4 = new TransformGroup(przesun_4);
        kostka_trans_4.addChild(kostka_4);
      
        ColorCube kostka_5 = new ColorCube(bok_kostki);
        Transform3D przesun_5 = new Transform3D();
        przesun_5.setTranslation(new Vector3f(0.5f,0.0f,-0.5f));
        TransformGroup kostka_trans_5 = new TransformGroup(przesun_5);
        kostka_trans_5.addChild(kostka_5);

        ColorCube kostka_6 = new ColorCube(bok_kostki);
        Transform3D przesun_6 = new Transform3D();
        przesun_6.setTranslation(new Vector3f(0.5f,0.5f,-0.5f));
        TransformGroup kostka_trans_6 = new TransformGroup(przesun_6);
        kostka_trans_6.addChild(kostka_6);

        ColorCube kostka_7 = new ColorCube(bok_kostki);
        Transform3D przesun_7 = new Transform3D();
        przesun_7.setTranslation(new Vector3f(0.5f,0.5f,0.5f));
        TransformGroup kostka_trans_7 = new TransformGroup(przesun_7);
        kostka_trans_7.addChild(kostka_7);
      
        ColorCube kostka_8 = new ColorCube(bok_kostki);
        Transform3D przesun_8 = new Transform3D();
        przesun_8.setTranslation(new Vector3f(0.5f,0.0f,0.5f));
        TransformGroup kostka_trans_8 = new TransformGroup(przesun_8);
        kostka_trans_8.addChild(kostka_8);

        ColorCube kostka_9 = new ColorCube(bok_kostki);
        Transform3D przesun_9 = new Transform3D();
        przesun_9.setTranslation(new Vector3f(0.5f,-0.5f,0.5f));
        TransformGroup kostka_trans_9 = new TransformGroup(przesun_9);
        kostka_trans_9.addChild(kostka_9);
      
      
      
        ColorCube kostka_10 = new ColorCube(bok_kostki);
        Transform3D przesun_10 = new Transform3D();
        przesun_10.setTranslation(new Vector3f(0.0f,0.5f,0.0f));
        TransformGroup kostka_trans_10 = new TransformGroup(przesun_10);
        kostka_trans_10.addChild(kostka_10);

        ColorCube kostka_11 = new ColorCube(bok_kostki);
        Transform3D przesun_11 = new Transform3D();
        przesun_11.setTranslation(new Vector3f(0.0f,-0.5f,0.0f));
        TransformGroup kostka_trans_11 = new TransformGroup(przesun_11);
        kostka_trans_11.addChild(kostka_11);

        ColorCube kostka_12 = new ColorCube(bok_kostki);
        Transform3D przesun_12 = new Transform3D();
        przesun_12.setTranslation(new Vector3f(0.0f,0.0f,0.5f));
        TransformGroup kostka_trans_12 = new TransformGroup(przesun_12);
        kostka_trans_12.addChild(kostka_12);

        ColorCube kostka_13 = new ColorCube(bok_kostki);
        Transform3D przesun_13 = new Transform3D();
        przesun_13.setTranslation(new Vector3f(0.0f,-0.5f,0.5f));
        TransformGroup kostka_trans_13 = new TransformGroup(przesun_13);
        kostka_trans_13.addChild(kostka_13);
      
        ColorCube kostka_14 = new ColorCube(bok_kostki);
        Transform3D przesun_14 = new Transform3D();
        przesun_14.setTranslation(new Vector3f(0.0f,0.5f,0.5f));
        TransformGroup kostka_trans_14 = new TransformGroup(przesun_14);
        kostka_trans_14.addChild(kostka_14);

        ColorCube kostka_15 = new ColorCube(bok_kostki);
        Transform3D przesun_15 = new Transform3D();
        przesun_15.setTranslation(new Vector3f(0.0f,0.0f,-0.5f));
        TransformGroup kostka_trans_15 = new TransformGroup(przesun_15);
        kostka_trans_15.addChild(kostka_15);

        ColorCube kostka_16 = new ColorCube(bok_kostki);
        Transform3D przesun_16 = new Transform3D();
        przesun_16.setTranslation(new Vector3f(0.0f,0.5f,-0.5f));
        TransformGroup kostka_trans_16 = new TransformGroup(przesun_16);
        kostka_trans_16.addChild(kostka_16);

        ColorCube kostka_17 = new ColorCube(bok_kostki);
        Transform3D przesun_17 = new Transform3D();
        przesun_17.setTranslation(new Vector3f(0.0f,-0.5f,-0.5f));
        TransformGroup kostka_trans_17 = new TransformGroup(przesun_17);
        kostka_trans_17.addChild(kostka_17);
      
      
        
        ColorCube kostka_18 = new ColorCube(bok_kostki);
        Transform3D przesun_18 = new Transform3D();
        przesun_18.setTranslation(new Vector3f(-0.5f,0.0f,0.5f));
        TransformGroup kostka_trans_18 = new TransformGroup(przesun_18);
        kostka_trans_18.addChild(kostka_18);

        ColorCube kostka_19 = new ColorCube(bok_kostki);
        Transform3D przesun_19 = new Transform3D();
        przesun_19.setTranslation(new Vector3f(-0.5f,-0.5f,0.5f));
        TransformGroup kostka_trans_19 = new TransformGroup(przesun_19);
        kostka_trans_19.addChild(kostka_19);

        ColorCube kostka_20 = new ColorCube(bok_kostki);
        Transform3D przesun_20 = new Transform3D();
        przesun_20.setTranslation(new Vector3f(-0.5f,0.5f,0.5f));
        TransformGroup kostka_trans_20 = new TransformGroup(przesun_20);
        kostka_trans_20.addChild(kostka_20);

        ColorCube kostka_21 = new ColorCube(bok_kostki);
        Transform3D przesun_21 = new Transform3D();
        przesun_21.setTranslation(new Vector3f(-0.5f,0.5f,0.0f));
        TransformGroup kostka_trans_21 = new TransformGroup(przesun_21);
        kostka_trans_21.addChild(kostka_21);

        ColorCube kostka_22 = new ColorCube(bok_kostki);
        Transform3D przesun_22 = new Transform3D();
        przesun_22.setTranslation(new Vector3f(-0.5f,0.0f,0.0f));
        TransformGroup kostka_trans_22 = new TransformGroup(przesun_22);
        kostka_trans_22.addChild(kostka_22);

        ColorCube kostka_23 = new ColorCube(bok_kostki);
        Transform3D przesun_23 = new Transform3D();
        przesun_23.setTranslation(new Vector3f(-0.5f,-0.5f,0.0f));
        TransformGroup kostka_trans_23 = new TransformGroup(przesun_23);
        kostka_trans_23.addChild(kostka_23);

        ColorCube kostka_24 = new ColorCube(bok_kostki);
        Transform3D przesun_24 = new Transform3D();
        przesun_24.setTranslation(new Vector3f(-0.5f,-0.5f,-0.5f));
        TransformGroup kostka_trans_24 = new TransformGroup(przesun_24);
        kostka_trans_24.addChild(kostka_24);

        ColorCube kostka_25 = new ColorCube(bok_kostki);
        Transform3D przesun_25 = new Transform3D();
        przesun_25.setTranslation(new Vector3f(-0.5f,0.0f,-0.5f));
        TransformGroup kostka_trans_25 = new TransformGroup(przesun_25);
        kostka_trans_25.addChild(kostka_25);

        ColorCube kostka_26 = new ColorCube(bok_kostki);
        Transform3D przesun_26 = new Transform3D();
        przesun_26.setTranslation(new Vector3f(-0.5f,0.5f,-0.5f));
        TransformGroup kostka_trans_26 = new TransformGroup(przesun_26);
        kostka_trans_26.addChild(kostka_26);
      
        

        Transform3D zmiana_osi = new Transform3D();
        
//        poniżej jest klasa reprezentująca oś obrotu w formie wektora
        AxisAngle4f os = new AxisAngle4f(2.0f, 0.0f, 0.0f, 0.0f);
        
//        ustawiam powyższy wektor jako oś obrotu
        zmiana_osi.setRotation(os);

        
//        tworzę tyle TG ile mam kostek i ustawiam w każdej z nich oś obrotu na środku
//        układu współrzędnych wzdłuż osi x
        TransformGroup ciekawe_1 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_2 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_3 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_4 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_5 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_6 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_7 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_8 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_9 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_10 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_11 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_12 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_13 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_14 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_15 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_16 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_17 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_18 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_19 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_20 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_21 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_22 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_23 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_24 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_25 = new TransformGroup(zmiana_osi);
        TransformGroup ciekawe_26 = new TransformGroup(zmiana_osi);
        
        
//        do stworzonych powyżej TG trzeba dodać poszczególne kostki,
//        a dokładniej kostki wraz z ich rozmieszczeniem
        ciekawe_1.addChild(kostka_trans_1);
        ciekawe_2.addChild(kostka_trans_2);
        ciekawe_3.addChild(kostka_trans_3);
        ciekawe_4.addChild(kostka_trans_4);
        ciekawe_5.addChild(kostka_trans_5);
        ciekawe_6.addChild(kostka_trans_6);
        ciekawe_7.addChild(kostka_trans_7);
        ciekawe_8.addChild(kostka_trans_8);
        ciekawe_9.addChild(kostka_trans_9);
        ciekawe_10.addChild(kostka_trans_10);
        ciekawe_11.addChild(kostka_trans_11);
        ciekawe_12.addChild(kostka_trans_12);
        ciekawe_13.addChild(kostka_trans_13);
        ciekawe_14.addChild(kostka_trans_14);
        ciekawe_15.addChild(kostka_trans_15);
        ciekawe_16.addChild(kostka_trans_16);
        ciekawe_17.addChild(kostka_trans_17);
        ciekawe_18.addChild(kostka_trans_18);
        ciekawe_19.addChild(kostka_trans_19);
        ciekawe_20.addChild(kostka_trans_20);
        ciekawe_21.addChild(kostka_trans_21);
        ciekawe_22.addChild(kostka_trans_22);
        ciekawe_23.addChild(kostka_trans_23);
        ciekawe_24.addChild(kostka_trans_24);
        ciekawe_25.addChild(kostka_trans_25);
        ciekawe_26.addChild(kostka_trans_26);
        
        
//        następnie do nadrzędnego węzła dodaję wszystkie TG ciekawe_....
        wezel_scena.addChild(ciekawe_1);
        wezel_scena.addChild(ciekawe_2);
        wezel_scena.addChild(ciekawe_3);
        wezel_scena.addChild(ciekawe_4);
        wezel_scena.addChild(ciekawe_5);
        wezel_scena.addChild(ciekawe_6);
        wezel_scena.addChild(ciekawe_7);
        wezel_scena.addChild(ciekawe_8);
        wezel_scena.addChild(ciekawe_9);
        wezel_scena.addChild(ciekawe_10);
        wezel_scena.addChild(ciekawe_11);
        wezel_scena.addChild(ciekawe_12);
        wezel_scena.addChild(ciekawe_13);
        wezel_scena.addChild(ciekawe_14);
        wezel_scena.addChild(ciekawe_15);
        wezel_scena.addChild(ciekawe_16);
        wezel_scena.addChild(ciekawe_17);
        wezel_scena.addChild(ciekawe_18);
        wezel_scena.addChild(ciekawe_19);
        wezel_scena.addChild(ciekawe_20);
        wezel_scena.addChild(ciekawe_21);
        wezel_scena.addChild(ciekawe_22);
        wezel_scena.addChild(ciekawe_23);
        wezel_scena.addChild(ciekawe_24);
        wezel_scena.addChild(ciekawe_25);
        wezel_scena.addChild(ciekawe_26);
      
        
        scena.addChild(wezel_scena);
        
      
//        tutaj z kolei tworzę instancje klas RotateBehavior, która będzie służyła
//        do zmiany elementów w czasie trwania pracy programu i jest opisana gdzie indziej
        awtBehavior_1 = new RotateBehavior(ciekawe_1);
        awtBehavior_2 = new RotateBehavior(ciekawe_2);
        awtBehavior_3 = new RotateBehavior(ciekawe_3);
        awtBehavior_4 = new RotateBehavior(ciekawe_4);
        awtBehavior_5 = new RotateBehavior(ciekawe_5);
        awtBehavior_6 = new RotateBehavior(ciekawe_6);
        awtBehavior_7 = new RotateBehavior(ciekawe_7);
        awtBehavior_8 = new RotateBehavior(ciekawe_8);
        awtBehavior_9 = new RotateBehavior(ciekawe_9);
        awtBehavior_10 = new RotateBehavior(ciekawe_10);
        awtBehavior_11 = new RotateBehavior(ciekawe_11);
        awtBehavior_12 = new RotateBehavior(ciekawe_12);
        awtBehavior_13 = new RotateBehavior(ciekawe_13);
        awtBehavior_14 = new RotateBehavior(ciekawe_14);
        awtBehavior_15 = new RotateBehavior(ciekawe_15);
        awtBehavior_16 = new RotateBehavior(ciekawe_16);
        awtBehavior_17 = new RotateBehavior(ciekawe_17);
        awtBehavior_18 = new RotateBehavior(ciekawe_18);
        awtBehavior_19 = new RotateBehavior(ciekawe_19);
        awtBehavior_20 = new RotateBehavior(ciekawe_20);
        awtBehavior_21 = new RotateBehavior(ciekawe_21);
        awtBehavior_22 = new RotateBehavior(ciekawe_22);
        awtBehavior_23 = new RotateBehavior(ciekawe_23);
        awtBehavior_24 = new RotateBehavior(ciekawe_24);
        awtBehavior_25 = new RotateBehavior(ciekawe_25);
        awtBehavior_26 = new RotateBehavior(ciekawe_26);
        
        
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
	    awtBehavior_1.setSchedulingBounds(bounds);
        awtBehavior_2.setSchedulingBounds(bounds);
        awtBehavior_3.setSchedulingBounds(bounds);
        awtBehavior_4.setSchedulingBounds(bounds);
        awtBehavior_5.setSchedulingBounds(bounds);
        awtBehavior_6.setSchedulingBounds(bounds);
        awtBehavior_7.setSchedulingBounds(bounds);
        awtBehavior_8.setSchedulingBounds(bounds);
        awtBehavior_9.setSchedulingBounds(bounds);
        awtBehavior_10.setSchedulingBounds(bounds);
        awtBehavior_11.setSchedulingBounds(bounds);
        awtBehavior_12.setSchedulingBounds(bounds);
        awtBehavior_13.setSchedulingBounds(bounds);
        awtBehavior_14.setSchedulingBounds(bounds);
        awtBehavior_15.setSchedulingBounds(bounds);
        awtBehavior_16.setSchedulingBounds(bounds);
        awtBehavior_17.setSchedulingBounds(bounds);
        awtBehavior_18.setSchedulingBounds(bounds);
        awtBehavior_19.setSchedulingBounds(bounds);
        awtBehavior_20.setSchedulingBounds(bounds);
        awtBehavior_21.setSchedulingBounds(bounds);
        awtBehavior_22.setSchedulingBounds(bounds);
        awtBehavior_23.setSchedulingBounds(bounds);
        awtBehavior_24.setSchedulingBounds(bounds);
        awtBehavior_25.setSchedulingBounds(bounds);
        awtBehavior_26.setSchedulingBounds(bounds);
        
        
//        dodajemy wszystkie RotateBehavior do wezel_scena
	    wezel_scena.addChild(awtBehavior_1);
        wezel_scena.addChild(awtBehavior_2);
        wezel_scena.addChild(awtBehavior_3);
        wezel_scena.addChild(awtBehavior_4);
        wezel_scena.addChild(awtBehavior_5);
        wezel_scena.addChild(awtBehavior_6);
        wezel_scena.addChild(awtBehavior_7);
        wezel_scena.addChild(awtBehavior_8);
        wezel_scena.addChild(awtBehavior_9);
        wezel_scena.addChild(awtBehavior_10);
        wezel_scena.addChild(awtBehavior_11);
        wezel_scena.addChild(awtBehavior_12);
        wezel_scena.addChild(awtBehavior_13);
        wezel_scena.addChild(awtBehavior_14);
        wezel_scena.addChild(awtBehavior_15);
        wezel_scena.addChild(awtBehavior_16);
        wezel_scena.addChild(awtBehavior_17);
        wezel_scena.addChild(awtBehavior_18);
        wezel_scena.addChild(awtBehavior_19);
        wezel_scena.addChild(awtBehavior_20);
        wezel_scena.addChild(awtBehavior_21);
        wezel_scena.addChild(awtBehavior_22);
        wezel_scena.addChild(awtBehavior_23);
        wezel_scena.addChild(awtBehavior_24);
        wezel_scena.addChild(awtBehavior_25);
        wezel_scena.addChild(awtBehavior_26);
        
        
//        poniższe linijki tworzą cztery linie, które tworzą kwadrat w przestrzeni
//        i pokazują która płaszczyzna jest obecnie zaznaczona
        LineArray line_1 = new LineArray(2, LineArray.COORDINATES);
        line_1.setCoordinate(0, new Point3f(0.0f, -1.0f, 1.0f));
        line_1.setCoordinate(1, new Point3f(0.0f, 1.0f, 1.0f));
        
        LineArray line_2 = new LineArray(2, LineArray.COORDINATES);
        line_2.setCoordinate(0, new Point3f(0.0f, -1.0f, -1.0f));
        line_2.setCoordinate(1, new Point3f(0.0f, 1.0f, -1.0f));
        
        LineArray line_3 = new LineArray(2, LineArray.COORDINATES);
        line_3.setCoordinate(0, new Point3f(0.0f, 1.0f, -1.0f));
        line_3.setCoordinate(1, new Point3f(0.0f, 1.0f, 1.0f));
        
        LineArray line_4 = new LineArray(2, LineArray.COORDINATES);
        line_4.setCoordinate(0, new Point3f(0.0f, -1.0f, -1.0f));
        line_4.setCoordinate(1, new Point3f(0.0f, -1.0f, 1.0f));

        
        TransformGroup indicator = new TransformGroup();
        indicator.addChild(new Shape3D(line_1));
        indicator.addChild(new Shape3D(line_2));
        indicator.addChild(new Shape3D(line_3));
        indicator.addChild(new Shape3D(line_4));
        
//        indicator_behavior to specjalna klasa do obracania i umieszczania
//        wskaźnika płaszczyzny na odpowiednim miejscu
        indicator_behavior = new RotateBehavior_indicator(indicator);
        indicator_behavior.setSchedulingBounds(bounds);
        
        wezel_scena.addChild(indicator_behavior);
        wezel_scena.addChild(indicator);
        
      return scena;
    }

    
    
//    główna klasa do obracania kostką - przechowuje informacje o tym gdzie znajdują
//    się poszczególne kostki oraz zmienia ich położenie, gdy zostaną wywołane
//    odpowiednie funkcje
    class Tablica_obrotow
    {
//        poniżej reprezentowane są poszczególne kostki w trzech warstwach (zamiast
//        tablicy trójwymiarowej)
        private int[][] pierwsza_warstwa = new int[3][3];
        private int[][] druga_warstwa = new int[3][3];
        private int[][] trzecia_warstwa = new int[3][3];
        
//        będzie służyła do wyboru który obrót ma zostać wykonany
        int pomocnicza = 0;

//        poniżej zadaję początkowe ułożenie poszczególnych kosteczek w całym układzie
        public Tablica_obrotow() 
        {
            trzecia_warstwa[0][0] = 20;
            trzecia_warstwa[0][1] = 14;
            trzecia_warstwa[0][2] = 7;
            trzecia_warstwa[1][0] = 18;
            trzecia_warstwa[1][1] = 12;
            trzecia_warstwa[1][2] = 8;
            trzecia_warstwa[2][0] = 19;
            trzecia_warstwa[2][1] = 13;
            trzecia_warstwa[2][2] = 9;

            druga_warstwa[0][0] = 21;
            druga_warstwa[0][1] = 10;
            druga_warstwa[0][2] = 1;
            druga_warstwa[1][0] = 22;
            druga_warstwa[1][1] = 0;
            druga_warstwa[1][2] = 2;
            druga_warstwa[2][0] = 23;
            druga_warstwa[2][1] = 11;
            druga_warstwa[2][2] = 3;

            pierwsza_warstwa[0][0] = 26;
            pierwsza_warstwa[0][1] = 16;
            pierwsza_warstwa[0][2] = 6;
            pierwsza_warstwa[1][0] = 25;
            pierwsza_warstwa[1][1] = 15;
            pierwsza_warstwa[1][2] = 5;
            pierwsza_warstwa[2][0] = 24;
            pierwsza_warstwa[2][1] = 17;
            pierwsza_warstwa[2][2] = 4;
        }
        
        
//        poniższe 9 funkcji działa na dokładnie tej samej zasadzie, czyli
//        jeżeli zostaną wywołane - zmieniają miejsce elementów w trzech tablicah
//        opisujących położenie poszczególnych kosteczek w całej kostce
        public void obrot_y1 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[2][1];
                trzecia_warstwa[2][1] = druga_warstwa[2][0];
                druga_warstwa[2][0] = pierwsza_warstwa[2][1];
                pierwsza_warstwa[2][1] = druga_warstwa[2][2];
                druga_warstwa[2][2] = pomocnicza;

                pomocnicza = trzecia_warstwa[2][2];
                trzecia_warstwa[2][2] = trzecia_warstwa[2][0];
                trzecia_warstwa[2][0] = pierwsza_warstwa[2][0];
                pierwsza_warstwa[2][0] = pierwsza_warstwa[2][2];
                pierwsza_warstwa[2][2] = pomocnicza;
            }
            else
            {
                pomocnicza = druga_warstwa[2][2];
                druga_warstwa[2][2] = pierwsza_warstwa[2][1];
                pierwsza_warstwa[2][1] = druga_warstwa[2][0];
                druga_warstwa[2][0] = trzecia_warstwa[2][1];
                trzecia_warstwa[2][1] = pomocnicza;
                
                pomocnicza = pierwsza_warstwa[2][2];
                pierwsza_warstwa[2][2] = pierwsza_warstwa[2][0];
                pierwsza_warstwa[2][0] = trzecia_warstwa[2][0];
                trzecia_warstwa[2][0] = trzecia_warstwa[2][2];
                trzecia_warstwa[2][2] = pomocnicza;
            }
            
            
//            wywołanie funkcji obrot_y1 informuje tylko o tym jaka płaszczyzna sie obraca,
//            natomiast o kierunku obrotu informuje zmienna boolowska
//            true oznacza obrót dodatni, a false ujemny (jak chodzi o obroty układów współrzędnych)
//            dlatego w zależności od tego, czy right == true czy right == false wywołujemy funkcję
//            z odpowiednim argumentem
            if (right)
                rotacja(OBROT_Y1_RIGHT);
            else
                rotacja(OBROT_Y1_LEFT);
        }
        
        
        public void obrot_y2 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[1][1];
                trzecia_warstwa[1][1] = druga_warstwa[1][0];
                druga_warstwa[1][0] = pierwsza_warstwa[1][1];
                pierwsza_warstwa[1][1] = druga_warstwa[1][2];
                druga_warstwa[1][2] = pomocnicza;

                pomocnicza = trzecia_warstwa[1][2];
                trzecia_warstwa[1][2] = trzecia_warstwa[1][0];
                trzecia_warstwa[1][0] = pierwsza_warstwa[1][0];
                pierwsza_warstwa[1][0] = pierwsza_warstwa[1][2];
                pierwsza_warstwa[1][2] = pomocnicza;
            }
            else 
            {
                pomocnicza = druga_warstwa[1][2];
                druga_warstwa[1][2] = pierwsza_warstwa[1][1];
                pierwsza_warstwa[1][1] = druga_warstwa[1][0];
                druga_warstwa[1][0] = trzecia_warstwa[1][1];
                trzecia_warstwa[1][1] = pomocnicza;
                
                pomocnicza = pierwsza_warstwa[1][2];
                pierwsza_warstwa[1][2] = pierwsza_warstwa[1][0];
                pierwsza_warstwa[1][0] = trzecia_warstwa[1][0];
                trzecia_warstwa[1][0] = trzecia_warstwa[1][2];
                trzecia_warstwa[1][2] = pomocnicza;
            }
            
            
            if (right)
                rotacja(OBROT_Y2_RIGHT);
            else
                rotacja(OBROT_Y2_LEFT);
        }
        
        
        public void obrot_y3 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[0][1];
                trzecia_warstwa[0][1] = druga_warstwa[0][0];
                druga_warstwa[0][0] = pierwsza_warstwa[0][1];
                pierwsza_warstwa[0][1] = druga_warstwa[0][2];
                druga_warstwa[0][2] = pomocnicza;

                pomocnicza = trzecia_warstwa[0][2];
                trzecia_warstwa[0][2] = trzecia_warstwa[0][0];
                trzecia_warstwa[0][0] = pierwsza_warstwa[0][0];
                pierwsza_warstwa[0][0] = pierwsza_warstwa[0][2];
                pierwsza_warstwa[0][2] = pomocnicza;
            }
            else
            {
                pomocnicza = druga_warstwa[0][2];
                druga_warstwa[0][2] = pierwsza_warstwa[0][1];
                pierwsza_warstwa[0][1] = druga_warstwa[0][0];
                druga_warstwa[0][0] = trzecia_warstwa[0][1];
                trzecia_warstwa[0][1] = pomocnicza;
                
                pomocnicza = pierwsza_warstwa[0][2];
                pierwsza_warstwa[0][2] = pierwsza_warstwa[0][0];
                pierwsza_warstwa[0][0] = trzecia_warstwa[0][0];
                trzecia_warstwa[0][0] = trzecia_warstwa[0][2];
                trzecia_warstwa[0][2] = pomocnicza;
            }
            
            
            if (right)
                rotacja(OBROT_Y3_RIGHT);
            else
                rotacja(OBROT_Y3_LEFT);
        }
        
        
        public void obrot_z1 (boolean right)
        {
            if (right)
            {
                pomocnicza = pierwsza_warstwa[0][1];
                pierwsza_warstwa[0][1] = pierwsza_warstwa[1][2];
                pierwsza_warstwa[1][2] = pierwsza_warstwa[2][1];
                pierwsza_warstwa[2][1] = pierwsza_warstwa[1][0];
                pierwsza_warstwa[1][0] = pomocnicza;

                pomocnicza = pierwsza_warstwa[0][2];
                pierwsza_warstwa[0][2] = pierwsza_warstwa[2][2];
                pierwsza_warstwa[2][2] = pierwsza_warstwa[2][0];
                pierwsza_warstwa[2][0] = pierwsza_warstwa[0][0];
                pierwsza_warstwa[0][0] = pomocnicza;
            }
            else
            {
                pomocnicza = pierwsza_warstwa[0][1];
                pierwsza_warstwa[0][1] = pierwsza_warstwa[1][0];
                pierwsza_warstwa[1][0] = pierwsza_warstwa[2][1];
                pierwsza_warstwa[2][1] = pierwsza_warstwa[1][2];
                pierwsza_warstwa[1][2] = pomocnicza;

                pomocnicza = pierwsza_warstwa[0][2];
                pierwsza_warstwa[0][2] = pierwsza_warstwa[0][0];
                pierwsza_warstwa[0][0] = pierwsza_warstwa[2][0];
                pierwsza_warstwa[2][0] = pierwsza_warstwa[2][2];
                pierwsza_warstwa[2][2] = pomocnicza;
            }
            
            
            if (right)
                rotacja(OBROT_Z1_RIGHT);
            else
                rotacja(OBROT_Z1_LEFT);
        }
        
        
        public void obrot_z2 (boolean right)
        {
            if (right)
            {
                pomocnicza = druga_warstwa[0][1];
                druga_warstwa[0][1] = druga_warstwa[1][2];
                druga_warstwa[1][2] = druga_warstwa[2][1];
                druga_warstwa[2][1] = druga_warstwa[1][0];
                druga_warstwa[1][0] = pomocnicza;

                pomocnicza = druga_warstwa[0][2];
                druga_warstwa[0][2] = druga_warstwa[2][2];
                druga_warstwa[2][2] = druga_warstwa[2][0];
                druga_warstwa[2][0] = druga_warstwa[0][0];
                druga_warstwa[0][0] = pomocnicza;                
            }
            else
            {
                pomocnicza = druga_warstwa[0][1];
                druga_warstwa[0][1] = druga_warstwa[1][0];
                druga_warstwa[1][0] = druga_warstwa[2][1];
                druga_warstwa[2][1] = druga_warstwa[1][2];
                druga_warstwa[1][2] = pomocnicza;

                pomocnicza = druga_warstwa[0][2];
                druga_warstwa[0][2] = druga_warstwa[0][0];
                druga_warstwa[0][0] = druga_warstwa[2][0];
                druga_warstwa[2][0] = druga_warstwa[2][2];
                druga_warstwa[2][2] = pomocnicza;
            }

            
            if (right)
                rotacja(OBROT_Z2_RIGHT);
            else
                rotacja(OBROT_Z2_LEFT);
        }
        
        
        public void obrot_z3 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[0][1];
                trzecia_warstwa[0][1] = trzecia_warstwa[1][2];
                trzecia_warstwa[1][2] = trzecia_warstwa[2][1];
                trzecia_warstwa[2][1] = trzecia_warstwa[1][0];
                trzecia_warstwa[1][0] = pomocnicza;

                pomocnicza = trzecia_warstwa[0][2];
                trzecia_warstwa[0][2] = trzecia_warstwa[2][2];
                trzecia_warstwa[2][2] = trzecia_warstwa[2][0];
                trzecia_warstwa[2][0] = trzecia_warstwa[0][0];
                trzecia_warstwa[0][0] = pomocnicza;
            }
            else
            {
                pomocnicza = trzecia_warstwa[0][1];
                trzecia_warstwa[0][1] = trzecia_warstwa[1][0];
                trzecia_warstwa[1][0] = trzecia_warstwa[2][1];
                trzecia_warstwa[2][1] = trzecia_warstwa[1][2];
                trzecia_warstwa[1][2] = pomocnicza;

                pomocnicza = trzecia_warstwa[0][2];
                trzecia_warstwa[0][2] = trzecia_warstwa[0][0];
                trzecia_warstwa[0][0] = trzecia_warstwa[2][0];
                trzecia_warstwa[2][0] = trzecia_warstwa[2][2];
                trzecia_warstwa[2][2] = pomocnicza;
            }
            
            
            if (right)
                rotacja(OBROT_Z3_RIGHT);
            else
                rotacja(OBROT_Z3_LEFT);
        }
        
        
        public void obrot_x1 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[1][0];
                trzecia_warstwa[1][0] = druga_warstwa[0][0];
                druga_warstwa[0][0] = pierwsza_warstwa[1][0];
                pierwsza_warstwa[1][0] = druga_warstwa[2][0];
                druga_warstwa[2][0] = pomocnicza;

                pomocnicza = trzecia_warstwa[0][0];
                trzecia_warstwa[0][0] = pierwsza_warstwa[0][0];
                pierwsza_warstwa[0][0] = pierwsza_warstwa[2][0];
                pierwsza_warstwa[2][0] = trzecia_warstwa[2][0];
                trzecia_warstwa[2][0] = pomocnicza;                
            }
            else
            {
                pomocnicza = trzecia_warstwa[1][0];
                trzecia_warstwa[1][0] = druga_warstwa[2][0];
                druga_warstwa[2][0] = pierwsza_warstwa[1][0];
                pierwsza_warstwa[1][0] = druga_warstwa[0][0];
                druga_warstwa[0][0] = pomocnicza;
                
                pomocnicza = trzecia_warstwa[0][0];
                trzecia_warstwa[0][0] = trzecia_warstwa[2][0];
                trzecia_warstwa[2][0] = pierwsza_warstwa[2][0];
                pierwsza_warstwa[2][0] = pierwsza_warstwa[0][0];
                pierwsza_warstwa[0][0] = pomocnicza;
            }

            
            if (right)
                rotacja(OBROT_X1_RIGHT);
            else
                rotacja(OBROT_X1_LEFT);
        }
        
        
        public void obrot_x2 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[1][1];
                trzecia_warstwa[1][1] = druga_warstwa[0][1];
                druga_warstwa[0][1] = pierwsza_warstwa[1][1];
                pierwsza_warstwa[1][1] = druga_warstwa[2][1];
                druga_warstwa[2][1] = pomocnicza;

                pomocnicza = trzecia_warstwa[0][1];
                trzecia_warstwa[0][1] = pierwsza_warstwa[0][1];
                pierwsza_warstwa[0][1] = pierwsza_warstwa[2][1];
                pierwsza_warstwa[2][1] = trzecia_warstwa[2][1];
                trzecia_warstwa[2][1] = pomocnicza;                
            }
            else
            {
                pomocnicza = trzecia_warstwa[1][1];
                trzecia_warstwa[1][1] = druga_warstwa[2][1];
                druga_warstwa[2][1] = pierwsza_warstwa[1][1];
                pierwsza_warstwa[1][1] = druga_warstwa[0][1];
                druga_warstwa[0][1] = pomocnicza;
                
                pomocnicza = trzecia_warstwa[0][1];
                trzecia_warstwa[0][1] = trzecia_warstwa[2][1];
                trzecia_warstwa[2][1] = pierwsza_warstwa[2][1];
                pierwsza_warstwa[2][1] = pierwsza_warstwa[0][1];
                pierwsza_warstwa[0][1] = pomocnicza;
            }

            
            if (right)
                rotacja(OBROT_X2_RIGHT);
            else
                rotacja(OBROT_X2_LEFT);
        }
        
        
        public void obrot_x3 (boolean right)
        {
            if (right)
            {
                pomocnicza = trzecia_warstwa[1][2];
                trzecia_warstwa[1][2] = druga_warstwa[0][2];
                druga_warstwa[0][2] = pierwsza_warstwa[1][2];
                pierwsza_warstwa[1][2] = druga_warstwa[2][2];
                druga_warstwa[2][2] = pomocnicza;

                pomocnicza = trzecia_warstwa[0][2];
                trzecia_warstwa[0][2] = pierwsza_warstwa[0][2];
                pierwsza_warstwa[0][2] = pierwsza_warstwa[2][2];
                pierwsza_warstwa[2][2] = trzecia_warstwa[2][2];
                trzecia_warstwa[2][2] = pomocnicza;
            }
            else 
            {
                pomocnicza = trzecia_warstwa[1][2];
                trzecia_warstwa[1][2] = druga_warstwa[2][2];
                druga_warstwa[2][2] = pierwsza_warstwa[1][2];
                pierwsza_warstwa[1][2] = druga_warstwa[0][2];
                druga_warstwa[0][2] = pomocnicza;
                
                pomocnicza = trzecia_warstwa[0][2];
                trzecia_warstwa[0][2] = trzecia_warstwa[2][2];
                trzecia_warstwa[2][2] = pierwsza_warstwa[2][2];
                pierwsza_warstwa[2][2] = pierwsza_warstwa[0][2];
                pierwsza_warstwa[0][2] = pomocnicza;
            }
            
            
            if (right)
                rotacja(OBROT_X3_RIGHT);
            else
                rotacja(OBROT_X3_LEFT);
        }
         
        
//        no i dochodzimy do funkcji, która zajmuje jakieś 750 linijek kodu i obraca poszczególne kostki
//        opis znajduje się przy poszczególnych elementach
        private void rotacja(int quel)
        {
//            9 elementowa tablica, która będzie przechowywać numery kostek, 
//            które mają być w tej chwili obracane
            int[] lista_kostek = new int[9];
            
//            poniższy if zapewnia, że cofając obrót ten obrót cofający nie trafi
//            na szczyt stosu
//            w przeciwnym wypadku cały czas zmienialibyśmy położenie między dwoma pozycjami
            if (czy_cofac)
                stos_cofnij.add(quel);
            
            czy_cofac = true;
            
            
//            i w zależności od argumentu przekazanego do funkcji rotacja() 
//            odpowiednio uzupełniamy tablicę lista_kostek
            switch (quel)
            {
                case OBROT_Y1_RIGHT:
                case OBROT_Y1_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[2][0];
                    lista_kostek[1] = pierwsza_warstwa[2][1];
                    lista_kostek[2] = pierwsza_warstwa[2][2];
                    lista_kostek[3] = druga_warstwa[2][0];
                    lista_kostek[4] = druga_warstwa[2][1];
                    lista_kostek[5] = druga_warstwa[2][2];
                    lista_kostek[6] = trzecia_warstwa[2][0];
                    lista_kostek[7] = trzecia_warstwa[2][1];
                    lista_kostek[8] = trzecia_warstwa[2][2];
                } break;
                case OBROT_Y2_RIGHT:
                case OBROT_Y2_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[1][0];
                    lista_kostek[1] = pierwsza_warstwa[1][1];
                    lista_kostek[2] = pierwsza_warstwa[1][2];
                    lista_kostek[3] = druga_warstwa[1][0];
                    lista_kostek[4] = druga_warstwa[1][1];
                    lista_kostek[5] = druga_warstwa[1][2];
                    lista_kostek[6] = trzecia_warstwa[1][0];
                    lista_kostek[7] = trzecia_warstwa[1][1];
                    lista_kostek[8] = trzecia_warstwa[1][2];
                } break;
                case OBROT_Y3_RIGHT:
                case OBROT_Y3_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[0][0];
                    lista_kostek[1] = pierwsza_warstwa[0][1];
                    lista_kostek[2] = pierwsza_warstwa[0][2];
                    lista_kostek[3] = druga_warstwa[0][0];
                    lista_kostek[4] = druga_warstwa[0][1];
                    lista_kostek[5] = druga_warstwa[0][2];
                    lista_kostek[6] = trzecia_warstwa[0][0];
                    lista_kostek[7] = trzecia_warstwa[0][1];
                    lista_kostek[8] = trzecia_warstwa[0][2];
                } break;
                case OBROT_X1_RIGHT:
                case OBROT_X1_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[0][0];
                    lista_kostek[1] = pierwsza_warstwa[1][0];
                    lista_kostek[2] = pierwsza_warstwa[2][0];
                    lista_kostek[3] = druga_warstwa[0][0];
                    lista_kostek[4] = druga_warstwa[1][0];
                    lista_kostek[5] = druga_warstwa[2][0];
                    lista_kostek[6] = trzecia_warstwa[0][0];
                    lista_kostek[7] = trzecia_warstwa[1][0];
                    lista_kostek[8] = trzecia_warstwa[2][0];
                } break;
                case OBROT_X2_RIGHT:
                case OBROT_X2_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[0][1];
                    lista_kostek[1] = pierwsza_warstwa[1][1];
                    lista_kostek[2] = pierwsza_warstwa[2][1];
                    lista_kostek[3] = druga_warstwa[0][1];
                    lista_kostek[4] = druga_warstwa[1][1];
                    lista_kostek[5] = druga_warstwa[2][1];
                    lista_kostek[6] = trzecia_warstwa[0][1];
                    lista_kostek[7] = trzecia_warstwa[1][1];
                    lista_kostek[8] = trzecia_warstwa[2][1];
                } break;
                case OBROT_X3_RIGHT:
                case OBROT_X3_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[0][2];
                    lista_kostek[1] = pierwsza_warstwa[1][2];
                    lista_kostek[2] = pierwsza_warstwa[2][2];
                    lista_kostek[3] = druga_warstwa[0][2];
                    lista_kostek[4] = druga_warstwa[1][2];
                    lista_kostek[5] = druga_warstwa[2][2];
                    lista_kostek[6] = trzecia_warstwa[0][2];
                    lista_kostek[7] = trzecia_warstwa[1][2];
                    lista_kostek[8] = trzecia_warstwa[2][2];
                } break;
                case OBROT_Z1_RIGHT:
                case OBROT_Z1_LEFT:
                {
                    lista_kostek[0] = pierwsza_warstwa[0][0];
                    lista_kostek[1] = pierwsza_warstwa[1][0];
                    lista_kostek[2] = pierwsza_warstwa[2][0];
                    lista_kostek[3] = pierwsza_warstwa[0][1];
                    lista_kostek[4] = pierwsza_warstwa[1][1];
                    lista_kostek[5] = pierwsza_warstwa[2][1];
                    lista_kostek[6] = pierwsza_warstwa[0][2];
                    lista_kostek[7] = pierwsza_warstwa[1][2];
                    lista_kostek[8] = pierwsza_warstwa[2][2];
                } break;
                case OBROT_Z2_RIGHT:
                case OBROT_Z2_LEFT:
                {
                    lista_kostek[0] = druga_warstwa[0][0];
                    lista_kostek[1] = druga_warstwa[1][0];
                    lista_kostek[2] = druga_warstwa[2][0];
                    lista_kostek[3] = druga_warstwa[0][1];
                    lista_kostek[4] = druga_warstwa[1][1];
                    lista_kostek[5] = druga_warstwa[2][1];
                    lista_kostek[6] = druga_warstwa[0][2];
                    lista_kostek[7] = druga_warstwa[1][2];
                    lista_kostek[8] = druga_warstwa[2][2];
                } break;
                case OBROT_Z3_RIGHT:
                case OBROT_Z3_LEFT:
                {
                    lista_kostek[0] = trzecia_warstwa[0][0];
                    lista_kostek[1] = trzecia_warstwa[1][0];
                    lista_kostek[2] = trzecia_warstwa[2][0];
                    lista_kostek[3] = trzecia_warstwa[0][1];
                    lista_kostek[4] = trzecia_warstwa[1][1];
                    lista_kostek[5] = trzecia_warstwa[2][1];
                    lista_kostek[6] = trzecia_warstwa[0][2];
                    lista_kostek[7] = trzecia_warstwa[1][2];
                    lista_kostek[8] = trzecia_warstwa[2][2];
                } break;
            }
            
            
//            wykonuję pętle 9 razy, żeby przejść po wszystkich elementach (kosteczkach),
//            które są w danej chwili obracane        
            for (int i = 0; i < 9; i++)
            {
//                w zależności od numeru kostki i zadanego obrotu wykonuje się 
//                odpowiedni obrót
                if (lista_kostek[i] == 1)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_1.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_1.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_1.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_1.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_1.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_1.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 2)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_2.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_2.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_2.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_2.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_2.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_2.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 3)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_3.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_3.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_3.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_3.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_3.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_3.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 4)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_4.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_4.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_4.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_4.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_4.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_4.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 5)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_5.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_5.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_5.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_5.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_5.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_5.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 6)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_6.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_6.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_6.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_6.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_6.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_6.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 7)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_7.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_7.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_7.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_7.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_7.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_7.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 8)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_8.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_8.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_8.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_8.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_8.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_8.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 9)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_9.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_9.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_9.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_9.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_9.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_9.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 10)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_10.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_10.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_10.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_10.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_10.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_10.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 11)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_11.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_11.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_11.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_11.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_11.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_11.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 12)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_12.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_12.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_12.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_12.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_12.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_12.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 13)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_13.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_13.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_13.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_13.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_13.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_13.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 14)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_14.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_14.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_14.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_14.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_14.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_14.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 15)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_15.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_15.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_15.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_15.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_15.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_15.rot_right();  break;   
                    }
                }
                else if (lista_kostek[i] == 16)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_16.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_16.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_16.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_16.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_16.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_16.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 17)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_17.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_17.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_17.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_17.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_17.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_17.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 18)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_18.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_18.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_18.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_18.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_18.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_18.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 19)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_19.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_19.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_19.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_19.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_19.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_19.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 20)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_20.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_20.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_20.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_20.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_20.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_20.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 21)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_21.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_21.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_21.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_21.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_21.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_21.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 22)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_22.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_22.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_22.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_22.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_22.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_22.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 23)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_23.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_23.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_23.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_23.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_23.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_23.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 24)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_24.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_24.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_24.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_24.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_24.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_24.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 25)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_25.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_25.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_25.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_25.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_25.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_25.rot_right();  break;
                    }
                }
                else if (lista_kostek[i] == 26)
                {
                    switch (quel)
                    {
                        case OBROT_Y1_RIGHT:    
                        case OBROT_Y2_RIGHT:
                        case OBROT_Y3_RIGHT:    awtBehavior_26.right();      break;
                        case OBROT_Y1_LEFT:
                        case OBROT_Y2_LEFT:
                        case OBROT_Y3_LEFT:     awtBehavior_26.left();       break;
                        case OBROT_X1_RIGHT:
                        case OBROT_X2_RIGHT:
                        case OBROT_X3_RIGHT:    awtBehavior_26.down();       break;
                        case OBROT_X1_LEFT:
                        case OBROT_X2_LEFT:    
                        case OBROT_X3_LEFT:     awtBehavior_26.up();         break;
                        case OBROT_Z1_RIGHT:
                        case OBROT_Z2_RIGHT:
                        case OBROT_Z3_RIGHT:    awtBehavior_26.rot_left();   break;
                        case OBROT_Z1_LEFT:
                        case OBROT_Z2_LEFT:
                        case OBROT_Z3_LEFT:     awtBehavior_26.rot_right();  break;
                    }
                }
            }            
        }
    }
   
   
   public static void main(String args[]){
      KostkaRubika kostka = new KostkaRubika();
   }


}



//klasa służąca do obrotów kwadratu w przestrzeni wskazującego
//która płaszczyzna jest obecnie zaznaczona
class RotateBehavior_indicator extends Behavior
{
    private TransformGroup transformGroup;
    private WakeupCriterion criterion;
    private Transform3D obrot = new Transform3D();
    private Transform3D translation = new Transform3D();
    private final int ROTATE = 1;
    
//    poniższe stałe zwiększają czytelność programu i ułatwiają pisanie
    private final int X1 = 2;
    private final int X2 = 3;
    private final int X3 = 4;
    private final int Y1 = 5;
    private final int Y2 = 6;
    private final int Y3 = 7;
    private final int Z1 = 8;
    private final int Z2 = 9;
    private final int Z3 = 10;
    
    
    
    RotateBehavior_indicator(TransformGroup tg) 
    {
        transformGroup = tg;
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    }
    
    public void initialize() 
    {
        criterion = new WakeupOnBehaviorPost(this, ROTATE);
        wakeupOn(criterion);
    }
    
    
//    w samym processStimulus ustawiam jedynie transformację, która z kolei jest
//    odpowiednio ustawiana w znajdujących się dalej funkcjach
    public void processStimulus(Enumeration criteria) 
    {
        transformGroup.setTransform(obrot);
        
        wakeupOn(criterion);            
    }
    
    
//    w zależności od wywołanej funkcji poniższe 9 funkcji ustawia obrót i
//    położenie wskaźnika wybranej płaszczyzny
    void x1()
    {
        obrot.rotX(0);
        translation.setTranslation(new Vector3f(-0.5f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void x2()
    {
        obrot.rotX(0);
        translation.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void x3()
    {
        obrot.rotX(0);
        translation.setTranslation(new Vector3f(0.5f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void y1()
    {
        obrot.rotZ(Math.PI/2);
        translation.setTranslation(new Vector3f(-0.5f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void y2()
    {
        obrot.rotZ(Math.PI/2);
        translation.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void y3()
    {
        obrot.rotZ(Math.PI/2);
        translation.setTranslation(new Vector3f(0.5f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void z1()
    {
        obrot.rotY(Math.PI/2);
        translation.setTranslation(new Vector3f(0.5f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void z2()
    {
        obrot.rotY(Math.PI/2);
        translation.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
    void z3()
    {
        obrot.rotY(Math.PI/2);
        translation.setTranslation(new Vector3f(-0.5f, 0.0f, 0.0f));
        obrot.mul(translation);
        postId(ROTATE);
    }
    
}




//    to jest główna klasa do zmiany położenia i orientacji elementów w czasie
//    działania programu
class RotateBehavior extends Behavior {

        private TransformGroup transformGroup;
        private Transform3D trans = new Transform3D();
        private Transform3D pomocnicza = new Transform3D();
        private WakeupCriterion criterion;
        private float angle = 0.0f;
        int wybierz = 0;

//        poniżej wprowadzam stałe, żeby wygodniej się później pracowało
        private final int ROTATE = 1;
        private final int LEFT = 2;
        private final int RIGHT = 3;
        private final int UP = 4;
        private final int DOWN = 5;
        private final int ROT_RIGHT = 6;
        private final int ROT_LEFT = 7;
        
//        natomiast tutaj są zdefiniowane osie obrotu wraz z kątem obrotu
        AxisAngle4f right = new AxisAngle4f(0.0f, 2.0f, 0.0f, (float)Math.toRadians(90.0));
        AxisAngle4f left = new AxisAngle4f(0.0f, -2.0f, 0.0f, (float)Math.toRadians(90.0));
        AxisAngle4f up = new AxisAngle4f(-2.0f, 0.0f, 0.0f, (float)Math.toRadians(90.0));
        AxisAngle4f down = new AxisAngle4f(2.0f, 0.0f, 0.0f, (float)Math.toRadians(90.0));
        AxisAngle4f rot_right = new AxisAngle4f(0.0f, 0.0f, -2.0f, (float)Math.toRadians(90.0));
        AxisAngle4f rot_left = new AxisAngle4f(0.0f, 0.0f, 2.0f, (float)Math.toRadians(90.0));
        AxisAngle4f pomocniczy = right;
        
        
        RotateBehavior(TransformGroup tg) {
            transformGroup = tg;
            tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        }

        
        public void initialize() {
            criterion = new WakeupOnBehaviorPost(this, ROTATE);
            wakeupOn(criterion);
        }

//        tutaj dochodzimy do metody, która jest wywoływana, gdy wywołamy
//        funkcję postId(ROTATE) za pośrednictwem odpowiednie metody tej klasy
        public void processStimulus(Enumeration criteria) 
        {
//            w każdym ifie dzieje się praktycznie to samo, tylko obroty są wykonywane 
//            w różne strony
//            parametr wybierz jest ustawiany za pomocą metod wywoływanych z zewnątrz
            if (wybierz == LEFT)
            {
//                ustawiam oś obrotu i kąt o jaki ma się obrócić (left)
//                oraz mnożę to z wcześniejszymi transformacjami
//                i wstawiam tą pomnożoną transformację do TG
                pomocnicza.setRotation(left);
                trans.mul(pomocnicza);
                transformGroup.setTransform(trans);
                
//                poniższe 5 linijek zamienia ze sobą osie obrotu,
//                krótko mówiąc obracając element wokół jednej osi zmieniamy położenie 
//                jego pozostałych osi i program nie działa jak powinien
//                dlatego po obrocie muszę tak je pozamieniać, żeby konkretne osie były 
//                cały czas na osiach podstawowego układu współrzędnych     
                pomocniczy = rot_left;
                rot_left = down;
                down = rot_right;
                rot_right = up;
                up = pomocniczy;
            }
            else if (wybierz == RIGHT)
            {
                pomocnicza.setRotation(right);
                trans.mul(pomocnicza);
                transformGroup.setTransform(trans);
                
                pomocniczy = rot_left;
                rot_left = up;
                up = rot_right;
                rot_right = down;
                down = pomocniczy;
            }
            else if (wybierz == UP)
            {
                pomocnicza.setRotation(up);
                trans.mul(pomocnicza);
                transformGroup.setTransform(trans);
                
                pomocniczy = rot_left;
                rot_left = left;
                left = rot_right;
                rot_right = right;
                right = pomocniczy;
            }   
            else if (wybierz == DOWN)
            {
                pomocnicza.setRotation(down);
                trans.mul(pomocnicza);
                transformGroup.setTransform(trans);
                
                pomocniczy = rot_left;
                rot_left = right;
                right = rot_right;
                rot_right = left;
                left = pomocniczy;
            }
            else if (wybierz == ROT_RIGHT)
            {
                pomocnicza.setRotation(rot_right);
                trans.mul(pomocnicza);
                transformGroup.setTransform(trans);
                
                pomocniczy = down;
                down = right;
                right = up;
                up = left;
                left = pomocniczy;
            }   
            else if (wybierz == ROT_LEFT)
            {
                pomocnicza.setRotation(rot_left);
                trans.mul(pomocnicza);
                transformGroup.setTransform(trans);
                
                pomocniczy = down;
                down = left;
                left = up;
                up = right;
                right = pomocniczy;
            }
                
//            to jest ustawianie kryterium na postawie którego ma się wykonać ta cała metoda
            wakeupOn(criterion);            
        }

        
//        wywołanie każdej z poniższych funkcji sprawia, że ustawiamy odpowiednio
//        parametr "wybierz", czyli de facto każda z poniższych funkcji sprawia, że
//        wykonuje się jeden konkretny if powyżej
//        i każda z tych funkcji wywołuję oczywiście metodę processStimulus    
        void left() 
        {
            wybierz = LEFT;
            postId(ROTATE);          
        }
        
        
        void right() 
        {
            wybierz = RIGHT;
            postId(ROTATE);          
        }
        
        
        void up()
        {
            wybierz = UP;
            postId(ROTATE);
        }
        
        
        void down()
        {
            wybierz = DOWN;
            postId(ROTATE);
        }
        
        
        void rot_right()
        {
            wybierz = ROT_RIGHT;
            postId(ROTATE);
        }
        
        
        void rot_left()
        {
            wybierz = ROT_LEFT;
            postId(ROTATE);
        }
        
        
    }



//ostatnia klasa, która tworzy osobne okno z instrukcją użytkowania symulatora
//kostki rubika
class Instrukcja extends JFrame
{
    
    Instrukcja()
    {  
        super("Instrukcja obsługi Kostki Rubika");
        setSize(600,400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        
        JTextArea oknoTekstowe = new JTextArea("Opcja 1:\n"
                + "Przyciskami 1-9 zmieniamy płaszczyznę obrotu\n"
                + "1-3 wzdłuż osi x\n"
                + "4-6 wzdłuż osi y\n"
                + "7-9 wzdłuż osi z\n"
                + "\n"
                + "Opcja 2:\n"
                + "Przyciskiem 'S'  zmieniamy obrót płaszczyzny obrotu\n"
                + "Przyciskiem 'D'  zmieniamy przesunięcie płaszczyzny obrotu\n"
                + "\n"
                + "Strzałkami góra/dół obracamy ścianę, która jest obecnie zaznaczona\n");
        
        Font czcionka = new Font("Arial", Font.PLAIN, 16);
        oknoTekstowe.setFont(czcionka);
        oknoTekstowe.setEditable(false);
        oknoTekstowe.setLineWrap(true);
        oknoTekstowe.setSize(550, 400);
        
        JPanel panel_instrukcji = new JPanel();
        panel_instrukcji.add(oknoTekstowe);
        
        add(panel_instrukcji);
    }
}