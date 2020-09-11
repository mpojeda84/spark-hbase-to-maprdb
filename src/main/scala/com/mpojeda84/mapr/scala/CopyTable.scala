package com.mpojeda84.mapr.scala

import org.apache.hadoop.hbase.client.Scan
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.TableName
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object CopyTable {
  def main(args: Array[String]) {

    val tableName = "Contacts";

    val sparkConf = new SparkConf().setAppName("Copy Table " + tableName )
    val sc = new SparkContext(sparkConf)

    try {
      val conf = HBaseConfiguration.create()

      val hbaseContext = new HBaseContext(sc, conf)

      val scan = new Scan()
      scan.setCaching(100)

      val getRdd = hbaseContext.hbaseRDD(TableName.valueOf(tableName), scan)

      //getRdd.foreach(v => println(Bytes.toString(v._1.get())))

      println("Length: " + getRdd.collect().length);
    } finally {
      sc.stop()
    }
  }

}