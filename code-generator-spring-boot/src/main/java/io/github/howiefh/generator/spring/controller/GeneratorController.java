package io.github.howiefh.generator.spring.controller;

import io.github.howiefh.generator.TemplateCodeGenerator;
import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.entity.Table;
import io.github.howiefh.generator.entity.TableColumn;
import io.github.howiefh.generator.service.GeneratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author fenghao on 2020/12/13
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("generator")
public class GeneratorController {
    @GetMapping("config")
    public Response<Config> basicConfig() {
        return Response.newSuccessResponse(Configuration.getConfig());
    }

    @PostMapping("config")
    public Response<String> updateConfig(@RequestBody Config config) throws IOException {
        GeneratorService.getInstance().updateBasicConfig(config);
        return Response.newSuccessResponse("");
    }


    @GetMapping("tables")
    public Response<Paging<Table>> listTables(String name) {
        List<Table> tables = GeneratorService.getInstance().listTables(name);
        return Response.newSuccessResponse(Paging.newPaging(tables.size(), tables));
    }

    @GetMapping("tables/{tableName}/columns")
    public Response<Table> listColumns(@PathVariable("tableName") String tableName) {
        Table table = GeneratorService.getInstance().listColumns(tableName);
        return Response.newSuccessResponse(table);
    }

    @PostMapping("tables/{tableName}/config/{model}/{className}")
    public Response<String> config(@RequestBody List<TableColumn> columns, @PathVariable("tableName") final String tableName
            , @PathVariable("model") String model, @PathVariable("className") String className) {
        GeneratorService.getInstance().config(columns, tableName, model, className);
        return Response.newSuccessResponse("");
    }

    @PostMapping("tables/{tableNames}/generate")
    public Response<String> generate(@PathVariable("tableNames") String tableNames) {
        GeneratorService.getInstance().updateTableConfig(tableNames);
        TemplateCodeGenerator.generate(Configuration.getConfig().isOverride());
        return Response.newSuccessResponse("");
    }

}
