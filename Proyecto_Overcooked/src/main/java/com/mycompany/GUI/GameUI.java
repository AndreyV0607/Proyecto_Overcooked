/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mycompany.objetos.*;
import com.mycompany.controller.GameController;
import javax.swing.border.TitledBorder;
//import com.mycompany.imagenes.*;


/**
 *
 * @author andreyvargassolis
 */
public class GameUI extends JFrame {

    
    protected final GameController controller; //pone el gamecontroller
    public Timer timer1s;     // reloj
    public Timer timer20s;    // generación de órdenes

    // visual
    // Info superior
    private JLabel lblTiempo;
    private JLabel lblPuntaje;

    // Cinta (5 espacios)
    private JLabel[] cintaSlots = new JLabel[5];
    private int cintaSeleccion = 0; 

    // Pila (vista vertical)
    private JPanel panelPila;

    // Órdenes (3 tarjetas)
    private JPanel panelOrdenes;

    // Botones acción
    private JButton btnEntregar;
    private JButton btnCancelar;

    //iconos
    private final int ICON_SIZE_CINTA = 96;
    private final int ICON_SIZE_PILA  = 48;
    private final int ICON_SIZE_RECETA = 20;

    private final ImageIcon icoPlaceholder = loadIcon("/imagenes/placeholder.png", ICON_SIZE_CINTA, ICON_SIZE_CINTA);
    private final ImageIcon icoBasurero   = loadIcon("/imagenes/basurero.png", 24, 24);
    private final ImageIcon icoEntregar   = loadIcon("/imagenes/entregar.png", 24, 24);
    private final ImageIcon icoCancelar   = loadIcon("/imagenes/x.png", 24, 24);

    public GameUI(GameController controller) {
        super("OverCooked-Fide — Estructuras (Swing)");
        this.controller = controller;
        construirUI();
        conectarEventos();
        configurarTimers();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
        System.out.println("pan: " + GameUI.class.getResource("/imagenes/pan.png"));
System.out.println("lechuga: " + GameUI.class.getResource("/imagenes/lechuga.png"));               //ya no se usa, fue de prueba por fotos
System.out.println("placeholder: " + GameUI.class.getResource("/imagenes/placeholder.png"));      // se conserva por posibles pruebas luego
*/
    }

    

    private void construirUI() { //setup de lo basico visual
        setLayout(new BorderLayout(10, 10));

        
        JPanel top = new JPanel(new BorderLayout());
        lblTiempo = etiquetaTitulo("Tiempo: 05:00");
        lblPuntaje = etiquetaTitulo("Puntaje: 0");
        top.add(lblTiempo, BorderLayout.WEST);
        top.add(lblPuntaje, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        
        JPanel center = new JPanel(new BorderLayout(10, 10));
        add(center, BorderLayout.CENTER);

        
        panelPila = new JPanel();
        panelPila.setLayout(new BoxLayout(panelPila, BoxLayout.Y_AXIS));
        panelPila.setBorder(bordeT("Hamburguesa (Pila)"));
        
        
        JScrollPane scrollPila = new JScrollPane(panelPila);
        scrollPila.setPreferredSize(new Dimension(180, 280));
        
        
        center.add(scrollPila, BorderLayout.WEST);

        // Panel Cinta 
        JPanel panelCinta = new JPanel(new BorderLayout());
        panelCinta.setBorder(bordeT("Cinta (clic para Tomar; tecla B tira)"));
        JPanel slots = new JPanel(new GridLayout(1, 5, 8, 8));
        slots.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        for (int i = 0; i < 5; i++) {
            JLabel lbl = new JLabel("", SwingConstants.CENTER);
            
            lbl.setPreferredSize(new Dimension(ICON_SIZE_CINTA + 10, ICON_SIZE_CINTA + 10));
            lbl.setOpaque(true);
            lbl.setBackground(new Color(245, 245, 245));
            lbl.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 2));
            
            final int pos = i;
            lbl.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    cintaSeleccion = pos;
                    controller.tomarIngrediente(pos);
                    refrescarVista();
                }
                
                
            });
            
            cintaSlots[i] = lbl;
            
            slots.add(lbl);
        }
        
        
        // agragar panel
        panelCinta.add(slots, BorderLayout.CENTER);

        // Botón basurero y selección
        JPanel bajoCinta = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        JLabel lblSel = new JLabel("Posición seleccionada: 1–5");
        JButton btnTirar = new JButton("Tirar seleccionado", icoBasurero);
        
        btnTirar.addActionListener(e -> {
            
            controller.tirarIngrediente(cintaSeleccion);
            refrescarVista();
        });
        
        
        bajoCinta.add(lblSel);
        bajoCinta.add(btnTirar);
        
        //agregar panel bajo cinta
        panelCinta.add(bajoCinta, BorderLayout.SOUTH);
        // agrega el centro
        center.add(panelCinta, BorderLayout.CENTER);

        // Panel Órdenes 
        panelOrdenes = new JPanel();
        panelOrdenes.setLayout(new BoxLayout(panelOrdenes, BoxLayout.Y_AXIS));
        panelOrdenes.setBorder(bordeT("Órdenes (FIFO, máx. 3)"));
        JScrollPane scrollOrdenes = new JScrollPane(panelOrdenes);
        scrollOrdenes.setPreferredSize(new Dimension(280, 280));
        center.add(scrollOrdenes, BorderLayout.EAST);

        //botones abajo
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        btnEntregar = new JButton("Entregar", icoEntregar);
        btnCancelar = new JButton("Cancelar armado", icoCancelar);
        btnEntregar.addActionListener(e -> {
            controller.entregar();
            
            refrescarVista();
        });
        
        
        btnCancelar.addActionListener(e -> {
            controller.cancelarArmado();
            
            refrescarVista();
        });
        
        
        bottom.add(btnEntregar);
        bottom.add(btnCancelar);
        add(bottom, BorderLayout.SOUTH);
    }

    

    private void conectarEventos() { //botones configuracion
        
        JComponent root = getRootPane();
        int cond = JComponent.WHEN_IN_FOCUSED_WINDOW;

        for (int i = 0; i < 5; i++) {
            final int pos = i;
            root.getInputMap(cond).put(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('1'+i), 0), "take"+i);
            root.getActionMap().put("take"+i, new AbstractAction() {
                
                @Override public void actionPerformed(ActionEvent e) {
                    
                    cintaSeleccion = pos;
                    controller.tomarIngrediente(pos);
                    refrescarVista();
                }
            });
        }
        
        // B -> tirar
        root.getInputMap(cond).put(KeyStroke.getKeyStroke('B'), "tirar");
        root.getActionMap().put("tirar", new AbstractAction() {
            
            @Override public void actionPerformed(ActionEvent e) {
                
                controller.tirarIngrediente(cintaSeleccion);
                refrescarVista();
            }
        });
        // E -> entregar
        root.getInputMap(cond).put(KeyStroke.getKeyStroke('E'), "entregar");
        root.getActionMap().put("entregar", new AbstractAction() {
            
            @Override public void actionPerformed(ActionEvent e) {
                controller.entregar();
                refrescarVista();
            }
        });
        // C -> cancelar armado
        root.getInputMap(cond).put(KeyStroke.getKeyStroke('C'), "cancelar");
        root.getActionMap().put("cancelar", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                
                controller.cancelarArmado();
                refrescarVista();
            }
        });
    }

    

    private void configurarTimers() { //setup de los tiempos, el total y el de los 20 de las ordenes
        timer1s = new Timer(1000, e -> {
            controller.tickSegundo();
            refrescarVista();
            if (!controller.isEnCurso()) {
                timer1s.stop();
                timer20s.stop();
                
            }
        });
        timer20s = new Timer(20000, e -> {
            controller.tickOrden();
            refrescarVista();
        });
    }

    

    public void refrescarVista() {
        // Tiempo y Puntaje
        lblTiempo.setText("Tiempo: " + controller.getTiempoTexto());
        lblPuntaje.setText("Puntaje: " + controller.getPuntaje());

        // Cinta
        Ingrediente[] vistaCinta = controller.getCintaVista();
        //debug para ver error que paso programando ver que realmente hay en los 5 slots
        System.out.println("CINTA = " + java.util.Arrays.toString(vistaCinta));

        for (int i = 0; i < 5; i++) {
            setIngredienteIcon(cintaSlots[i], vistaCinta[i], ICON_SIZE_CINTA);
            
            cintaSlots[i].setBorder(BorderFactory.createLineBorder(i == cintaSeleccion ? new Color(0,120,215) : new Color(200,200,200), 2));
        }

        // Pila de abajo a arriba
        panelPila.removeAll();
        Ingrediente[] pilaArr = controller.getPilaVista();
        for (int i = 0; i < pilaArr.length; i++) {
            JLabel l = new JLabel();
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
            setIngredienteIcon(l, pilaArr[i], ICON_SIZE_PILA);
            panelPila.add(l);
            
        }
        
        
        panelPila.revalidate();
        panelPila.repaint();

        
        // Órdenes 
        panelOrdenes.removeAll();
        // Orden actual (resaltada)
        Orden actual = controller.getOrdenActual();
        panelOrdenes.add(cardOrden(actual, true));
        // En espera
        Orden[] espera = controller.getOrdenesEnEspera();
        for (int i = 0; i < espera.length; i++) {
            panelOrdenes.add(Box.createVerticalStrut(8));
            panelOrdenes.add(cardOrden(espera[i], false));
        }
        panelOrdenes.revalidate();
        panelOrdenes.repaint();

        // Habilitar y   deshabilitar por estado
        boolean activo = controller.isEnCurso();
        btnEntregar.setEnabled(activo);
        btnCancelar.setEnabled(activo);
        for (JLabel lbl : cintaSlots) lbl.setEnabled(activo);
        
        
    }
    

    // Tarjeta de una orden 
    private JComponent cardOrden(Orden o, boolean actual) {
        
        JPanel card = new JPanel(new BorderLayout(8, 4));
        card.setBorder(BorderFactory.createCompoundBorder(
                
                BorderFactory.createLineBorder(actual ? new Color(0,120,215) : new Color(210,210,210), actual ? 2 : 1),
                BorderFactory.createEmptyBorder(6,6,6,6)
        ));
        if (o == null) {
            
            JLabel vacio = new JLabel("Sin orden", SwingConstants.CENTER);
            vacio.setForeground(new Color(150,150,150));
            card.add(vacio, BorderLayout.CENTER);
            return card;
        }

        // Imagen principal
        
        ImageIcon mainIcon = selectOrderIcon(o);
        JLabel img = new JLabel(scaleOrPlaceholder(mainIcon, 64, 64));
        card.add(img, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel nombre = new JLabel(o.getNombre());
        nombre.setFont(nombre.getFont().deriveFont(Font.BOLD, 14f));
        JLabel pts = new JLabel(o.getPuntos() + " pts");
        pts.setForeground(new Color(0,120,0));

        JPanel receta = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        Ingrediente[] req = o.getRequeridos();
        for (int i = 0; i < req.length; i++) {
            
            receta.add(new JLabel(loadIcon(iconPath(req[i]), ICON_SIZE_RECETA, ICON_SIZE_RECETA)));
        }

        info.add(nombre);
        info.add(pts);
        info.add(receta);
        card.add(info, BorderLayout.CENTER);

        return card;
    }

    // Funciones para settear imagenes 

    private ImageIcon selectOrderIcon(Orden o) {
        
        Ingrediente[] r = o.getRequeridos();
        
        for (int i = 0; i < r.length; i++) {
            if (r[i] == Ingrediente.carne) {
                return loadIcon(iconPath(Ingrediente.carne), 64, 64);
            }
        }
        return loadIcon(iconPath(r[0]), 64, 64);
    }

    private void setIngredienteIcon(JLabel label, Ingrediente ing, int size) {
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);

        if (ing == null) {
            label.setIcon(icoPlaceholder);
            label.setText("vacío");
            label.setToolTipText("vacío");
            label.setBackground(new Color(245, 245, 245));
        } else {
            ImageIcon icon = loadIcon(iconPath(ing), size, size);
            if (icon != null) {
                label.setIcon(icon);
                label.setText(ing.name());              //  texto debajo
                label.setToolTipText(ing.name());       
            } else {
                // Si falló la imagen, al menos que se vea el nombre. Fue para probar porque no subia imagnes
                label.setIcon(icoPlaceholder);
                label.setText(ing.name());
                label.setToolTipText(ing.name());
            }
            // colorcito suave por ingrediente, ya que de moemnto no se logro imagenes en todas para identificar
            if (ing == Ingrediente.pan) {
                label.setBackground(new Color(255, 250, 230));
            } else if (ing == Ingrediente.carne) {
                label.setBackground(new Color(255, 235, 235));
            } else if (ing == Ingrediente.queso) {
                label.setBackground(new Color(255, 250, 210));
            } else if (ing == Ingrediente.lechuga) {
                label.setBackground(new Color(235, 255, 235));
            }
        }
    }

    private String iconPath(Ingrediente ing) { // rutas de imagenes
        switch (ing) {
            case pan:
                return "/imagenes/pan.png";
            case carne:
                return "/imagenes/carne.png";
            case queso:
                return "/imagenes/queso.png";
            case lechuga:
                return "/imagenes/lechuga.png";
            default:
                return "/imagenes/placeholder.png";
        }
    }

    /////////////// setups de las fotos
    private ImageIcon scaleOrPlaceholder(ImageIcon base, int w, int h) {
        if (base == null) {
            return icoPlaceholder;
        }
        Image img = base.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url == null) {
                return null;
            }
            ImageIcon raw = new ImageIcon(url);
            Image img = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception ex) {
            return null;
        }
    }

    private TitledBorder bordeT(String t) {
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(220,220,220)), t);
    }

    private JLabel etiquetaTitulo(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 16f));
        return l;
    }
    
}

    