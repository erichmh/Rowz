package com.twitter.rowz.jobs

import com.twitter.gizzard.jobs.UnboundJob
import com.twitter.xrayspecs.Time
import com.twitter.xrayspecs.TimeConversions._


object CreateParser extends gizzard.jobs.UnboundJobParser[ForwardingManager] {
  def apply(attributes: Map[String, Any]) = {
    new Create(
      attributes("id").asInstanceOf[Long],
      attributes("name").asInstanceOf[String],
      Time(attributes("at").asInstanceOf[Int].seconds))
  }
}

case class Create(id: Long, name: String, at: Time) extends UnboundJob[ForwardingManager] {
  def toMap = {
    Map("id" -> id, "name" -> name, "at" -> at.inSeconds)
  }

  def apply(forwardingManager: ForwardingManager) = {
    forwardingManager(id).create(id, name, at)
  }
}