package common.enums;

import org.junit.jupiter.api.Test;
import server.controller.Controller;

import java.util.Formatter;

import static org.junit.jupiter.api.Assertions.*;

class ControllerMethodsEnumTest {

    @Test
    void help() {
        System.out.println(ControllerMethodsEnum.help());
    }
}