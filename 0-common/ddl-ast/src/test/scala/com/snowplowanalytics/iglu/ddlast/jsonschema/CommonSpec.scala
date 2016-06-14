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

class CommonSpec extends Specification with ValidationMatchers { def is = s2"""
  Check JSON Schema common properties
    parse string-typed Schema $e1
    parse union-typed Schema $e2
  """

  def e1 = {

    val schema = parse(
      """
        |{
        |  "type": "string"
        |}
      """.stripMargin)

    Schema.parse(schema) must beSome(Schema(`type` = Some(CommonProperties.String)))
  }


  def e2 = {

    val schema = parse(
      """
        |{
        |  "type": ["string", "null"]
        |}
      """.stripMargin)

    Schema.parse(schema) must beSome(Schema(`type` = Some(CommonProperties.Product(List(CommonProperties.String, CommonProperties.Null)))))
  }
}
