package ifpr.pgua.eic.tads.contatos.controllers;


import io.javalin.http.Context;
import io.javalin.http.Handler;

public class IndexController {


    public Handler get = (Context ctx) ->{
        ctx.render("templates/index.peb");
    };

    public Handler getBiteBlitz = (Context ctx) ->{
        ctx.render("public/html/index.html");
    };

    public Handler getFormularioBiteBlitz = (Context ctx) ->{
        ctx.render("public/html/addLanche.html");
    };
}
