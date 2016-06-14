/*
 * Copyright (c) 2012-2016 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.iglu.ddlast.jsonschema

// json4s
import org.json4s._
import org.json4s.jackson.JsonMethods.parse

// specs2
import org.specs2.Specification
import org.specs2.scalaz.ValidationMatchers

import json4s.Json4sToSchema._

class ParseSpec extends Specification with ValidationMatchers { def is = s2"""
  Check JSON Schema string specification
    parse big nested Schema $e1
  """

  def e1 = {

    val schema = parse(
      """
        |{
        |  "type": "object",
        |  "properties": {
        |    "someString": {
        |      "type": "string"
        |    },
        |    "netstedObject": {
        |      "type": "object",
        |      "properties": {
        |        "nestedKey": {
        |          "type": "null"
        |        },
        |        "deeply": {
        |          "type": "object",
        |          "properties": {
        |            "blueDeep": {
        |              "type": "integer",
        |              "minimum": 0,
        |              "maximum": 32767
        |            },
        |            "deepUnionTypeArray": {
        |              "type": "array",
        |              "items": {
        |                "type": [
        |                  "object",
        |                  "string",
        |                  "null",
        |                  "integer"
        |                ],
        |                "properties": {
        |                  "foo": {
        |                    "type": "string"
        |                  }
        |                },
        |                "additionalProperties": false,
        |                "minimum": 0,
        |                "maximum": 32767
        |              }
        |            }
        |          },
        |          "additionalProperties": false
        |        }
        |      },
        |      "additionalProperties": false
        |    },
        |    "emptyObject": {
        |      "type": "object",
        |      "properties": {},
        |      "additionalProperties": false
        |    },
        |    "simpleArray": {
        |      "type": "array",
        |      "items": {
        |        "type": "integer",
        |        "minimum": 0,
        |        "maximum": 32767
        |      }
        |    },
        |    "ipAddress": {
        |      "type": "string",
        |      "format": "ipv4"
        |    }
        |  },
        |  "additionalProperties": false
        |}
      """.stripMargin)

    import ObjectProperties._
    import NumberProperties._
    import ArrayProperties._
    import CommonProperties._

    implicit def optconv[A](a: A): Option[A] = Some(a)

    // TODO: format
    val resultSchema = Schema(
      `type` = Object,
      properties = Properties(Map(
        "ipAddress" -> Schema(
          `type` = String
        ),
        "someString" -> Schema(
          `type` = String
        ),
        "netstedObject" -> Schema(
          `type` = Object,
          properties = Properties(Map(
            "nestedKey" -> Schema(
              `type` = Null
            ),
            "deeply" -> Schema(
              `type` = Object,
              properties = Properties(Map(
                "blueDeep" -> Schema(
                  `type` = Integer,
                  minimum = IntegerMinimum(0),
                  maximum = IntegerMaximum(32767)
                ),
                "deepUnionTypeArray" -> Schema(
                  `type` = Array,
                  items = ListItems(Schema(
                    `type` = Product(List(Object, String, Null, Integer)),
                    properties = Properties(Map(
                      "foo" -> Schema(
                        `type` = String
                      )
                    )),
                    additionalProperties = AdditionalProperties(false),
                    minimum = IntegerMinimum(0),
                    maximum = IntegerMaximum(32767)
                  ))
                )
              )),
              additionalProperties = AdditionalProperties(false)
            )
          )),
          additionalProperties = AdditionalProperties(false)
        ),
        "emptyObject" -> Schema(
          `type` = Object,
          properties = Properties(Map()),
          additionalProperties = AdditionalProperties(false)
        ),
        "simpleArray" -> Schema(
          `type` = Array,
          items = ListItems(Schema(
            `type` = Integer,
            minimum = IntegerMinimum(0),
            maximum = IntegerMaximum(32767)
          ))
        )
      )),
      additionalProperties = AdditionalProperties(false)
    )

    Schema.parse(schema) must beSome(resultSchema)
  }
}
