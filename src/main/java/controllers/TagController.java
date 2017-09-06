package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagMapDao;
import generated.tables.records.ReceiptsRecord;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final ReceiptDao receipts;
    final TagMapDao tagMap;

    public TagController(ReceiptDao receipts, TagMapDao tagMap) {

        this.receipts = receipts;
        this.tagMap = tagMap;
    }

    @PUT
    @Path("/tags/{tag}")
    public void toggleTag(@PathParam("tag") String tagName, String body) {

        int receiptId = Integer.parseInt(body);
        tagMap.toggle(tagName, receiptId);

    }

    @GET
    @Path("/tags/{tag}")
    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tagName) {

        List<ReceiptsRecord> receiptRecords = tagMap.getAllReceipts(tagName);
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());

    }

}


