package com.sksamuel.avro4s.schema

import com.sksamuel.avro4s.examples.UppercasePkg.ClassInUppercasePackage
import com.sksamuel.avro4s.internal.SchemaEncoder
import org.scalatest.{Matchers, WordSpec}

class BasicSchemasTest extends WordSpec with Matchers {

  "SchemaEncoder" should {
    "accept booleans" in {
      case class Test(booly: Boolean)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/boolean.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept bytes" in {
      case class Test(bytes: Array[Byte])
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/bytes.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept strings" in {
      case class Test(str: String)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/string.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept integer" in {
      case class Test(inty: Int)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/integer.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept longs" in {
      case class Test(foo: Long)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/long.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept double" in {
      case class Test(double: Double)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/double.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept float" in {
      case class Test(float: Float)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/float.avsc"))
      val schema = SchemaEncoder[Test].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "support recursive types" in {
      val schema = SchemaEncoder[Recursive].encode
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/recursive.json"))
      schema.toString(true) shouldBe expected.toString(true)
    }
    "support mutually recursive types" in {
      val schema = SchemaEncoder[MutRec1].encode
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/mutrec.json"))
      schema.toString(true) shouldBe expected.toString(true)
    }
    "support types nested in uppercase packages" in {
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/nested_in_uppercase_pkg.json"))
      val schema = SchemaEncoder[ClassInUppercasePackage].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept nested case classes" in {
      case class Nested(goo: String)
      case class NestedTest(foo: String, nested: Nested)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/nested.json"))
      val schema = SchemaEncoder[NestedTest].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept multiple nested case classes" in {
      case class Inner(goo: String)
      case class Middle(inner: Inner)
      case class Outer(middle: Middle)
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/nested_multiple.json"))
      val schema = SchemaEncoder[Outer].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
    "accept deep nested structure" in {
      val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/deepnested.json"))
      val schema = SchemaEncoder[Level1].encode
      schema.toString(true) shouldBe expected.toString(true)
    }
  }
}

case class Level4(str: Map[String, String])
case class Level3(level4: Level4)
case class Level2(level3: Level3)
case class Level1(level2: Level2)

case class MutRec1(payload: Int, children: List[MutRec2])
case class MutRec2(payload: String, children: List[MutRec1])

case class Recursive(payload: Int, next: Option[Recursive])
