package es.codeurjc.daw.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.service.MuseumObjectService;

// This controller has been deprecated because its URL mapping collided
// with the more elaborate viewObject method in UserController.  The
// logic for displaying an object is now handled there, which also adds
// user-specific attributes for logged-in users.
//
// Keeping the class around for reference, but without the
// @Controller annotation Spring will ignore it and the duplicate
// mapping error disappears.
//@Controller
public class MuseumObjectController {

    // not used at runtime
}
