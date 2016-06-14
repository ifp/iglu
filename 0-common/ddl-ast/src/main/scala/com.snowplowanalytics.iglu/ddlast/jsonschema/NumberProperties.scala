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

object NumberProperties {

  // TODO: methods `getAsRoundInt` and `getAsDouble`

  sealed trait MultipleOf
  case class NumberMultipleOf(value: BigDecimal) extends MultipleOf
  case class IntegerMultipleOf(value: BigInt) extends MultipleOf

  sealed trait Minimum
  case class NumberMinimum(value: BigDecimal) extends Minimum
  case class IntegerMinimum(value: BigInt) extends Minimum

  sealed trait Maximum
  case class NumberMaximum(value: BigDecimal) extends Maximum
  case class IntegerMaximum(value: BigInt) extends Maximum

}


