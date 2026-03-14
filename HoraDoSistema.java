package com.mycompany.horadosistema;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class HoraDoSistema {

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter
            .ofPattern("EEEE, dd 'de' MMMM 'de' yyyy HH:mm:ss");
    private static final Font FONTE_UI = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 14);

    private JFrame frame;
    private JPanel root;
    private JPanel cabecalho;
    private JPanel acoes;
    private JLabel titulo;
    private JTabbedPane abas;
    private JButton botaoAtualizar;
    private JToggleButton botaoTema;
    private JTextArea textoSobre;

    private final List<JPanel> cards = new ArrayList<>();
    private final List<JPanel> paineisAbas = new ArrayList<>();
    private final List<JLabel> labelsFixas = new ArrayList<>();
    private final List<JLabel> labelsValores = new ArrayList<>();

    private Tema temaAtual = Tema.claro();

    private JLabel valorDataHora;
    private JLabel valorIdioma;
    private JLabel valorPais;
    private JLabel valorSistema;
    private JLabel valorJava;
    private JLabel valorResolucaoToolkit;
    private JLabel valorResolucaoDisplay;
    private JLabel valorMonitor;
    private JLabel valorDisplayInfo;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            aplicarLookAndFeel();
            new HoraDoSistema().criarInterface();
        });
    }

    private static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Usa o look and feel padrao caso o sistema nao suporte o nativo.
        }
    }

    private void criarInterface() {
        frame = new JFrame("Hora do Sistema");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(760, 500));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(criarIconeJanela());

        root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        titulo = new JLabel("Hora do Sistema", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));

        botaoAtualizar = new JButton("Atualizar agora");
        botaoAtualizar.setFocusPainted(false);
        botaoAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoAtualizar.addActionListener(e -> atualizarDados());

        botaoTema = new JToggleButton("Tema escuro");
        botaoTema.setFocusPainted(false);
        botaoTema.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoTema.addActionListener(e -> alternarTema());

        cabecalho = new JPanel(new BorderLayout(16, 0));
        cabecalho.setOpaque(false);
        cabecalho.add(titulo, BorderLayout.WEST);

        acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acoes.setOpaque(false);
        acoes.add(botaoTema);
        acoes.add(botaoAtualizar);
        cabecalho.add(acoes, BorderLayout.EAST);

        root.add(cabecalho, BorderLayout.NORTH);

        abas = new JTabbedPane();
        abas.setFont(new Font("Segoe UI", Font.BOLD, 13));
        abas.setUI(new BasicTabbedPaneUI());
        abas.addTab("Visao geral", criarAbaVisaoGeral());
        abas.addTab("Tela", criarAbaTela());
        abas.addTab("Sobre", criarAbaSobre());
        abas.addChangeListener(e -> atualizarCoresAbas());
        root.add(abas, BorderLayout.CENTER);

        aplicarTema();

        frame.setContentPane(root);
        frame.pack();
        frame.setVisible(true);

        atualizarDados();
        new Timer(1000, e -> atualizarDados()).start();
    }

    private JPanel criarAbaVisaoGeral() {
        JPanel painel = criarPainelAba();
        valorDataHora = adicionarLinha(painel, "Data e hora");
        valorIdioma = adicionarLinha(painel, "Idioma");
        valorPais = adicionarLinha(painel, "Pais");
        valorSistema = adicionarLinha(painel, "Sistema operacional");
        valorJava = adicionarLinha(painel, "Java");
        return painel;
    }

    private JPanel criarAbaTela() {
        JPanel painel = criarPainelAba();
        valorResolucaoToolkit = adicionarLinha(painel, "Resolucao (toolkit)");
        valorResolucaoDisplay = adicionarLinha(painel, "Resolucao (display)");
        valorMonitor = adicionarLinha(painel, "Monitor principal");
        valorDisplayInfo = adicionarLinha(painel, "Detalhes de video");
        return painel;
    }

    private JPanel criarAbaSobre() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        paineisAbas.add(painel);

        textoSobre = new JTextArea();
        textoSobre.setEditable(false);
        textoSobre.setLineWrap(true);
        textoSobre.setWrapStyleWord(true);
        textoSobre.setFont(FONTE_UI);
        textoSobre.setText(
                "Mini app Swing para visualizar dados do sistema em tempo real.\n\n"
                + "Recursos:\n"
                + "- Atualizacao automatica por segundo\n"
                + "- Tema claro/escuro\n"
                + "- Informacoes de sistema e tela em abas\n"
                + "- Icone de janela customizado"
        );
        textoSobre.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        painel.add(textoSobre, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelAba() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painel.setOpaque(true);
        paineisAbas.add(painel);
        return painel;
    }

    private JLabel adicionarLinha(JPanel parent, String titulo) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JLabel rotulo = new JLabel(titulo + ":");
        rotulo.setFont(FONTE_LABEL);

        JLabel valor = new JLabel("-");
        valor.setFont(FONTE_UI);

        card.add(rotulo, BorderLayout.WEST);
        card.add(valor, BorderLayout.CENTER);

        cards.add(card);
        labelsFixas.add(rotulo);
        labelsValores.add(valor);

        parent.add(card);
        return valor;
    }

    private void alternarTema() {
        if (botaoTema.isSelected()) {
            temaAtual = Tema.escuro();
            botaoTema.setText("Tema claro");
        } else {
            temaAtual = Tema.claro();
            botaoTema.setText("Tema escuro");
        }
        aplicarTema();
    }

    private void aplicarTema() {
        root.setBackground(temaAtual.fundo);
        titulo.setForeground(temaAtual.titulo);
        abas.setBackground(temaAtual.superficie);
        abas.setForeground(temaAtual.texto);
        abas.setOpaque(true);
        cabecalho.setBackground(temaAtual.fundo);
        acoes.setBackground(temaAtual.fundo);

        for (JPanel painelAba : paineisAbas) {
            painelAba.setBackground(temaAtual.superficie);
            painelAba.setForeground(temaAtual.texto);
        }

        atualizarCoresAbas();

        for (JPanel card : cards) {
            card.setBackground(temaAtual.card);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(temaAtual.borda),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));
        }

        for (JLabel fixo : labelsFixas) {
            fixo.setForeground(temaAtual.rotulo);
        }

        for (JLabel valor : labelsValores) {
            valor.setForeground(temaAtual.texto);
        }

        botaoAtualizar.setBackground(temaAtual.botaoPrimario);
        botaoAtualizar.setForeground(temaAtual.botaoPrimarioTexto);
        botaoAtualizar.setBorder(BorderFactory.createLineBorder(temaAtual.botaoPrimarioBorda));

        botaoTema.setBackground(temaAtual.botaoSecundario);
        botaoTema.setForeground(temaAtual.botaoSecundarioTexto);
        botaoTema.setBorder(BorderFactory.createLineBorder(temaAtual.botaoSecundarioBorda));

        if (textoSobre != null) {
            textoSobre.setBackground(temaAtual.card);
            textoSobre.setForeground(temaAtual.texto);
            textoSobre.setCaretColor(temaAtual.texto);
                textoSobre.setSelectionColor(temaAtual.botaoPrimario);
                textoSobre.setSelectedTextColor(temaAtual.botaoPrimarioTexto);
            textoSobre.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(temaAtual.borda),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));
        }

        frame.repaint();
    }

    private void atualizarCoresAbas() {
        int abaSelecionada = abas.getSelectedIndex();
        for (int i = 0; i < abas.getTabCount(); i++) {
            boolean selecionada = i == abaSelecionada;
            abas.setBackgroundAt(i, selecionada ? temaAtual.abaSelecionada : temaAtual.abaNaoSelecionada);
            abas.setForegroundAt(i, selecionada ? temaAtual.abaTextoSelecionada : temaAtual.abaTextoNaoSelecionada);
        }
    }

    private Image criarIconeJanela() {
        BufferedImage imagem = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagem.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(25, 118, 210));
        g2.fillRoundRect(4, 4, 56, 56, 14, 14);

        g2.setColor(new Color(255, 255, 255));
        g2.fillOval(14, 14, 36, 36);
        g2.setColor(new Color(25, 118, 210));
        g2.drawOval(14, 14, 36, 36);

        g2.setColor(new Color(25, 118, 210));
        g2.drawLine(32, 32, 32, 22);
        g2.drawLine(32, 32, 41, 36);

        g2.dispose();
        return imagem;
    }

    private void atualizarDados() {
        Locale localePadrao = Locale.getDefault();
        LocalDateTime agora = LocalDateTime.now();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensaoTela = toolkit.getScreenSize();

        GraphicsDevice monitorPrincipal = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        valorDataHora.setText(agora.format(DATA_FORMATTER.withLocale(localePadrao)));
        valorIdioma.setText(localePadrao.getDisplayLanguage(localePadrao));
        valorPais.setText(localePadrao.getDisplayCountry(localePadrao));
        valorResolucaoToolkit.setText(dimensaoTela.width + "x" + dimensaoTela.height);
        valorResolucaoDisplay.setText(
                monitorPrincipal.getDisplayMode().getWidth() + "x" + monitorPrincipal.getDisplayMode().getHeight()
        );
        valorMonitor.setText(monitorPrincipal.getIDstring());
        valorDisplayInfo.setText(
            monitorPrincipal.getDisplayMode().getRefreshRate() + " Hz | "
                + monitorPrincipal.getDisplayMode().getBitDepth() + " bits"
        );
        valorSistema.setText(System.getProperty("os.name") + " " + System.getProperty("os.version"));
        valorJava.setText(System.getProperty("java.version"));
    }

        private record Tema(
            Color fundo,
            Color superficie,
            Color card,
            Color borda,
            Color titulo,
            Color rotulo,
            Color texto,
                Color abaSelecionada,
                Color abaNaoSelecionada,
                Color abaTextoSelecionada,
                Color abaTextoNaoSelecionada,
                    Color botaoPrimario,
                    Color botaoPrimarioTexto,
                    Color botaoPrimarioBorda,
            Color botaoSecundario,
                    Color botaoSecundarioTexto,
                    Color botaoSecundarioBorda
        ) {

        private static Tema claro() {
            return new Tema(
                new Color(242, 246, 252),
                new Color(238, 243, 250),
                Color.WHITE,
                new Color(220, 228, 240),
                new Color(23, 45, 89),
                new Color(63, 82, 115),
                new Color(35, 47, 69),
                    new Color(24, 111, 221),
                    new Color(214, 225, 244),
                    Color.WHITE,
                    new Color(39, 58, 92),
                    new Color(24, 111, 221),
                    Color.WHITE,
                    new Color(16, 85, 173),
                    new Color(232, 238, 248),
                    new Color(29, 46, 77),
                    new Color(182, 196, 220)
            );
        }

        private static Tema escuro() {
            return new Tema(
                new Color(24, 28, 37),
                new Color(30, 35, 47),
                new Color(39, 45, 60),
                new Color(62, 70, 91),
                new Color(218, 231, 255),
                new Color(165, 188, 232),
                new Color(228, 236, 255),
                        new Color(57, 128, 224),
                        new Color(36, 44, 60),
                        Color.WHITE,
                        new Color(188, 206, 238),
                        new Color(57, 128, 224),
                        Color.WHITE,
                        new Color(43, 102, 182),
                        new Color(57, 67, 90),
                        new Color(236, 243, 255),
                        new Color(102, 116, 146)
            );
        }
        }
}
