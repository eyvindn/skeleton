package dao;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagmapRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedList;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.TAGMAP;
import static generated.Tables.RECEIPTS;

public class TagMapDao {
    DSLContext dsl;

    public TagMapDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public Boolean toggle(String tagName, int receiptId) {

        TagmapRecord exists = dsl.selectFrom(TAGMAP).where(TAGMAP.RECEIPTID.eq(receiptId).and(TAGMAP.NAME.eq(tagName))).fetchOne();

        if(exists != null){
            dsl.delete(TAGMAP).where(TAGMAP.RECEIPTID.eq(receiptId).and(TAGMAP.NAME.eq(tagName))).execute();
        } else {
            TagmapRecord someTagmapRecord = dsl.insertInto(TAGMAP, TAGMAP.NAME, TAGMAP.RECEIPTID).values(tagName, receiptId).returning(TAGMAP.ID).fetchOne();
            checkState(someTagmapRecord != null && someTagmapRecord.getId() != null, "Insert failed");
        }

        return true;
    }

    public List<ReceiptsRecord> getAllReceipts(String tagName) {
        List<Integer> taggedReceipts = dsl.selectFrom(TAGMAP).where(TAGMAP.NAME.eq(tagName)).fetch(TAGMAP.RECEIPTID, Integer.class);

        List<ReceiptsRecord> outputList = new LinkedList<ReceiptsRecord>();

        for(Integer receiptId : taggedReceipts){
            ReceiptsRecord someTag = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.eq(receiptId)).fetchOne();
            outputList.add(someTag);
        }

        return outputList;
    }
}
