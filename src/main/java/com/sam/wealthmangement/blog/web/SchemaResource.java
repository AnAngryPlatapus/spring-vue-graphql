package com.sam.wealthmangement.blog.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.Directives;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.Directive;
import io.leangen.graphql.metadata.strategy.query.DirectiveBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.wealthmangement.blog.service.PostService;
import com.sam.wealthmangement.blog.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("schema")
public class SchemaResource {

    private final ObjectMapper objectMapper;
    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public HttpEntity<Object> getSchema() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages("com.sam.wealthmangement")
                .withOperationsFromSingletons(postService, userService)
                .generate();
        SchemaPrinter schemaPrinter = new SchemaPrinter(SchemaPrinter.Options.defaultOptions()
                 .includeScalarTypes(true)
                 .includeExtendedScalarTypes(true)
                 .includeSchemaDefintion(true)
                 .includeDirectives(false));
        return new ResponseEntity<>(schemaPrinter.print(schema), HttpStatus.OK);
    }
}
