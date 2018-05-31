package mindware.com;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import elemental.json.JsonArray;
import kaesdingeling.hybridmenu.HybridMenu;
import kaesdingeling.hybridmenu.builder.HybridMenuBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuSubMenuBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuLabelBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuSubContentBuilder;
import kaesdingeling.hybridmenu.components.NotificationCenter;
import kaesdingeling.hybridmenu.data.DesignItem;
import kaesdingeling.hybridmenu.data.MenuConfig;
import kaesdingeling.hybridmenu.data.enums.EMenuComponents;
import kaesdingeling.hybridmenu.data.enums.EMenuStyle;
import kaesdingeling.hybridmenu.data.leftmenu.MenuButton;
import kaesdingeling.hybridmenu.data.leftmenu.MenuSubMenu;
import kaesdingeling.hybridmenu.data.top.TopMenuButton;
import kaesdingeling.hybridmenu.data.top.TopMenuLabel;
import kaesdingeling.hybridmenu.data.top.TopMenuSubContent;
import mindware.com.model.MenuOption;
import mindware.com.model.Rol;
import mindware.com.page.SettingsPage;
import mindware.com.page.ThemeBuilderPage;
import mindware.com.service.RolService;
import mindware.com.view.*;
import mindware.com.view.LoginForm;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI  implements ClientConnector.DetachListener {

    private HybridMenu hybridMenu = null;
    private NotificationCenter notiCenter = null;
    private LoginForm loginForm = null;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        UI.getCurrent().setPollInterval(5000);
        loginForm = new LoginForm();
        setContent(loginForm);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


    public void callMenu(String login, Integer userId, Integer rolId) {
        MenuConfig menuConfig = new MenuConfig();
        menuConfig.setDesignItem(DesignItem.getDarkDesign());

        notiCenter = new NotificationCenter(5000);

        hybridMenu = HybridMenuBuilder.get()
                .setContent(new VerticalLayout())
                .setMenuComponent(EMenuComponents.LEFT_WITH_TOP)
                .setConfig(menuConfig)
//    			.withNotificationCenter(notiCenter)
                .build();

        UI.getCurrent().getNavigator().addView(UserForm.class.getSimpleName(), UserForm.class);
//        UI.getCurrent().getNavigator().addView(UserPasswordForm.class.getSimpleName(), new UserPasswordForm(userId));
        UI.getCurrent().getNavigator().addView(ClassPeriodForm.class.getSimpleName(), new ClassPeriodForm());
        UI.getCurrent().getNavigator().addView(ParameterForm.class.getSimpleName(), ParameterForm.class);
        UI.getCurrent().getNavigator().addView(PaymentFeeForm.class.getSimpleName(), PaymentFeeForm.class);
        UI.getCurrent().getNavigator().addView(PaymentPrintForm.class.getSimpleName(), PaymentPrintForm.class);
        UI.getCurrent().getNavigator().addView(StudentForm.class.getSimpleName(), StudentForm.class);
        UI.getCurrent().getNavigator().addView(TypeFeeForm.class.getSimpleName(), TypeFeeForm.class);
        UI.getCurrent().getNavigator().addView(UserForm.class.getSimpleName(), UserForm.class);
        UI.getCurrent().getNavigator().addView(RolForm.class.getSimpleName(), RolForm.class);
        UI.getCurrent().getNavigator().addView(PaymentPlanForm.class.getSimpleName(), PaymentPlanForm.class);
        UI.getCurrent().getNavigator().addView(PaymentEditForm.class.getSimpleName(), PaymentEditForm.class);
        UI.getCurrent().getNavigator().addView(PaymentCashForm.class.getSimpleName(), PaymentCashForm.class);
//    	UI.getCurrent().getNavigator().addView(ThemeBuilderPage.class.getSimpleName(), ThemeBuilderPage.class);
        UI.getCurrent().getNavigator().addView(SettingsPage.class.getSimpleName(), SettingsPage.class);
        UI.getCurrent().getNavigator().addView(LatePaymentForm.class.getSimpleName(), LatePaymentForm.class);
        UI.getCurrent().getNavigator().setErrorView(LoginForm.class);



        if(hybridMenu.getMenuComponents().equals(EMenuComponents.ONLY_LEFT))
            buildLeftMenu(hybridMenu,rolId);
        else
        if(hybridMenu.getMenuComponents().equals(EMenuComponents.LEFT_WITH_TOP)) {
            buildLeftMenu(hybridMenu, rolId);
            buildTopOnlyMenu(hybridMenu,userId);
        }else
            buildTopOnlyMenu(hybridMenu,userId);

        getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                if (event.getOldView() != null && event.getOldView().getClass().getSimpleName().equals(ThemeBuilderPage.class.getSimpleName())) {
                    hybridMenu.switchTheme(DesignItem.getDarkDesign());
                }
                return true;
            }
        });

        setContent(hybridMenu);
        VaadinSession.getCurrent().setAttribute(HybridMenu.class, hybridMenu);

        JavaScript.getCurrent().addFunction("aboutToClose", new JavaScriptFunction() {
            private static final long serialVersionUID = 1L;
            @Override
            public void call(JsonArray arguments) {
                detach();
            }
        });

        Page.getCurrent().getJavaScript().execute("window.onbeforeunload = function (e) { var e = e || window.event; aboutToClose(); return; };");
    }

    private void buildTopOnlyMenu(HybridMenu hybridMenu, Integer userId) {

        TopMenuSubContent userAccountMenu = TopMenuSubContentBuilder.get()
                .setButtonCaption("Usuario")
                .setButtonIcon(new ThemeResource("images/profilDummy.jpg"))
                .addButtonStyleName(EMenuStyle.ICON_RIGHT)
                .addButtonStyleName(EMenuStyle.PROFILVIEW)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .build(hybridMenu);

        userAccountMenu.addButton("Cambiar password").addClickListener(clickEvent -> {

            UI.getCurrent().getNavigator().navigateTo(UserPasswordForm.class.getSimpleName());
        });


        TopMenuButtonBuilder.get()
                .setCaption("Vista menu")
                .setIcon(VaadinIcons.EXPAND_SQUARE)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .addClickListener(e -> hybridMenu.setLeftMenuMinimal(!hybridMenu.isLeftMenuMinimal()))
                .build(hybridMenu);

        TopMenuButton notiButton = TopMenuButtonBuilder.get()
                .setIcon(VaadinIcons.BELL_O)
                .setAlignment(Alignment.MIDDLE_RIGHT)
                .build(hybridMenu);

        notiCenter.setNotificationButton(notiButton);

        TopMenuLabel label = TopMenuLabelBuilder.get()
                .setCaption("<b>COBRO CUOTAS</b>")
                .setIcon(new ThemeResource("images/Logo.png"))
                .build(hybridMenu);

        label.getComponent().addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(UserForm.class.getSimpleName());
        });

//
    }



    private void buildLeftMenu(HybridMenu hybridMenu,  Integer rolId) {
        RolService rolService = new RolService();
        Rol rol = rolService.findAllRolMenuOptionByRolId(rolId);
        List<MenuOption> menuOptionList = new ArrayList<>(); // rol.getMenuOption();
        String[] listOptions = new String[1]; //new String[menuOptionList.size()];
        int i=0;
        for(MenuOption menuOption:menuOptionList){
            listOptions[i] = menuOption.getOptionId().toString();
            i+=1;
        }
        listOptions[0] = "0";



        if (Arrays.asList(listOptions).contains("0")) {
            MenuButton importDataButton = LeftMenuButtonBuilder.get()
                    .withCaption("Gestión periodos")
                    .withIcon(VaadinIcons.CALENDAR)
                    .withNavigateTo(ClassPeriodForm.class)
                    .build();

            hybridMenu.addLeftMenuButton(importDataButton);
        }
        if (Arrays.asList(listOptions).contains("0")) {
            MenuButton registerDataButton = LeftMenuButtonBuilder.get()
                    .withCaption("Registrar/Editar Alumnos")
                    .withIcon(VaadinIcons.CALENDAR_USER)
                    .withNavigateTo(StudentForm.class)
                    .build();

            hybridMenu.addLeftMenuButton(registerDataButton);
        }

        if (Arrays.asList(listOptions).contains("0") || Arrays.asList(listOptions).contains("0") ) {
            MenuSubMenu paymentList = LeftMenuSubMenuBuilder.get()
                    .setCaption("Pagos")
                    .setIcon(VaadinIcons.CASH)
//				.setConfig(hybridMenu.getConfig())
                    .build(hybridMenu);
            if (Arrays.asList(listOptions).contains("0")) {
                paymentList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                        .withCaption("Importar pagos")
                        .withIcon(VaadinIcons.MONEY_DEPOSIT)
                        .withNavigateTo(PaymentFeeForm.class)
                        .build());
            }
            if (Arrays.asList(listOptions).contains("0")) {
                paymentList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                        .withCaption("Pagos por caja")
                        .withIcon(VaadinIcons.MONEY_EXCHANGE)
                        .withNavigateTo(PaymentCashForm.class)
                        .build());
            }
            if (Arrays.asList(listOptions).contains("0")) {
                paymentList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                        .withCaption("Crear plan de pagos")
                        .withIcon(VaadinIcons.MONEY_DEPOSIT)
                        .withNavigateTo(PaymentPlanForm.class)
                        .build());
            }
            if (Arrays.asList(listOptions).contains("0")) {
                paymentList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                        .withCaption("Mora en pagos")
                        .withIcon(VaadinIcons.MONEY_WITHDRAW)
                        .withNavigateTo(LatePaymentForm.class)
                        .build());
            }
            if (Arrays.asList(listOptions).contains("0")) {
                paymentList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                        .withCaption("Editar detalle pagos")
                        .withIcon(VaadinIcons.EDIT)
                        .withNavigateTo(PaymentEditForm.class)
                        .build());
            }
            if (Arrays.asList(listOptions).contains("0")) {
                paymentList.addLeftMenuButton(LeftMenuButtonBuilder.get()
                        .withCaption("Reimprimir comprobantes")
                        .withIcon(VaadinIcons.PRINT)
                        .withNavigateTo(PaymentPrintForm.class)
                        .build());
            }


        }
        if (Arrays.asList(listOptions).contains("0")) {
            MenuButton TypeFeeButton = LeftMenuButtonBuilder.get()
                    .withCaption("Tipos de cuotas")
                    .withIcon(VaadinIcons.PALETE)
                    .withNavigateTo(TypeFeeForm.class)
                    .build();
            hybridMenu.addLeftMenuButton(TypeFeeButton);
        }
        if (Arrays.asList(listOptions).contains("0")) {
            MenuButton ParameterButton = LeftMenuButtonBuilder.get()
                    .withCaption("Parametros")
                    .withIcon(VaadinIcons.PACKAGE)
                    .withNavigateTo(ParameterForm.class)
                    .build();
            hybridMenu.addLeftMenuButton(ParameterButton);
        }
        if (Arrays.asList(listOptions).contains("0")) {
            MenuButton UserButton = LeftMenuButtonBuilder.get()
                    .withCaption("Usuarios")
                    .withIcon(VaadinIcons.USERS)
                    .withNavigateTo(UserForm.class)
                    .build();
            hybridMenu.addLeftMenuButton(UserButton);
        }
        if (Arrays.asList(listOptions).contains("0")) {
            MenuButton RolButton = LeftMenuButtonBuilder.get()
                    .withCaption("Roles")
                    .withIcon(VaadinIcons.FILE_CODE)
                    .withNavigateTo(RolForm.class)
                    .build();
            hybridMenu.addLeftMenuButton(RolButton);
        }

        if (Arrays.asList(listOptions).contains("0")) {
            MenuSubMenu demoSettings = LeftMenuSubMenuBuilder.get()
                    .setCaption("Preferencia")
                    .setIcon(VaadinIcons.COGS)
                    .setConfig(hybridMenu.getConfig())
                    .build(hybridMenu);

            LeftMenuButtonBuilder.get()
                    .withCaption("Tema de color Blanco")
                    .withIcon(VaadinIcons.PALETE)
                    .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteDesign()))
                    .build(demoSettings);

            LeftMenuButtonBuilder.get()
                    .withCaption("Tema de color Azul")
                    .withIcon(VaadinIcons.PALETE)
                    .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteBlueDesign()))
                    .build(demoSettings);

            LeftMenuButtonBuilder.get()
                    .withCaption("Tema color Obscuro")
                    .withIcon(VaadinIcons.PALETE)
                    .withClickListener(e -> hybridMenu.switchTheme(DesignItem.getDarkDesign()))
                    .build(demoSettings);

            LeftMenuButtonBuilder.get()
                    .withCaption("Alternar vista minima")
                    .withIcon(VaadinIcons.EXPAND_SQUARE)
                    .withClickListener(e -> hybridMenu.setLeftMenuMinimal(!hybridMenu.isLeftMenuMinimal()))
                    .build(demoSettings);
        }


        MenuButton ExitButton = LeftMenuButtonBuilder.get()
                .withCaption("Salir")
                .withIcon(VaadinIcons.EXIT)
                .withClickListener(clickEvent -> {
                    Page.getCurrent().setLocation( "/school" );
                    VaadinSession.getCurrent().close();
                })
                .build();
        hybridMenu.addLeftMenuButton(ExitButton);
    }



    @Override
    public void detach(DetachEvent event) {
        getUI().close();
    }

    public HybridMenu getHybridMenu() {
        return hybridMenu;
    }
}
