package com.sksamuel.avro4s.internal

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericRecord, IndexedRecord}
import org.apache.avro.specific.SpecificRecord

case class InternalRecord(schema: Schema, values: Vector[AnyRef]) extends GenericRecord with SpecificRecord with IndexedRecord {

  import scala.collection.JavaConverters._

  override def put(key: String, v: scala.Any): Unit = throw new UnsupportedOperationException("This implementation of Record is immutable")
  override def put(i: Int, v: scala.Any): Unit = throw new UnsupportedOperationException("This implementation of Record is immutable")

  override def get(key: String): AnyRef = {
    val index = schema.getFields.asScala.indexWhere(_.name == key)
    get(index)
  }

  override def get(i: Int): AnyRef = values(i)
  override def getSchema: Schema = schema
}
