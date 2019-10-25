package io.github.sentenza.hacktoberfest

import java.lang.reflect.Method
import java.util.concurrent.atomic.AtomicInteger

import io.github.sentenza.hacktoberfest.util.{ArrayUtil, GenericUtil}
import io.github.sentenza.hacktoberfest.algos.{ImmutableSorting, MutableSorting, Sorting}

import scala.annotation.tailrec
import scala.util.{Success, Try}

/*
 * HacktoberFest - Scala Algorhitms
 * Copyright (C) 2018 sentenza
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

object MenuIO {
  private val heading =
    """
      _  _         _   _       _             ___       _     ___ __  _ ___
     | || |__ _ __| |_| |_ ___| |__  ___ _ _| __|__ __| |_  |_  )  \/ ( _ )
     | __ / _` / _| / /  _/ _ \ '_ \/ -_) '_| _/ -_|_-<  _|  / / () | / _ \
     |_||_\__,_\__|_\_\\__\___/_.__/\___|_| |_|\___/__/\__| /___\__/|_\___/

    """

  private val gplDisclaimer =
    """
    HacktoberFest Scala Algorhitms Copyright (C) 2018-2019  @sentenza
    This program comes with ABSOLUTELY NO WARRANTY.
    This is free software, and you are welcome to redistribute it
    under certain conditions. All the details can be found at:
    https://github.com/sentenza/hacktoberfest-scala-algorithms/blob/master/LICENSE.
    """

  /**
    * This function should be called at the very beginning of the Main execution
    * to fetch the disclaimer message and the project Logo to be printed out
    */
  def printDisclaimer() { println(heading + gplDisclaimer) }

  private val noOp = () => ()

  def readNumberInputs = scala.io.StdIn.readLine().split(",").map(_.toInt)

  case class MenuEntry(selector: Int, display: String, code: () => Unit)

  private val entries =
    List(
      MenuEntry(
        1,
        "Sorting algorithms",
        () => {
          println("You chose sorting\n")
          renderInteractiveMenu(
            List(
              MenuEntry(2, "MutableSorting", () => {
                println("You chose mutable sorting.")
                // renderInteractiveMenu(createMethodMenuEntries(MutableSorting))
                renderInteractiveMenu()
              }),
              MenuEntry(1, "ImmutableSorting", () => {
                println("You chose immutable sorting.")
                // renderInteractiveMenu(createMethodMenuEntries(ImmutableSorting))
                renderInteractiveMenu()
              }),
              MenuEntry(0, "Quit sorting", noOp)
            )
          )
        }
      ),
      MenuEntry(0, "Quit the program", () => System.exit(0))
    )
  @tailrec
  def renderInteractiveMenu(entries: List[MenuEntry] = entries): Unit = {
    println("Please choose:")
    entries.foreach {
      case MenuEntry(num, label, _) =>
        println(s"$num: $label")
    }

    Try(scala.io.StdIn.readInt()) match {
      case Success(0) =>
        ()
      case Success(choice) if entries.exists(_.selector == choice) =>
        entries.find(_.selector == choice).foreach {
          case MenuEntry(_, _, code) => code()
        }
        renderInteractiveMenu()
      case _ =>
        println("Invalid selection\n")
        renderInteractiveMenu()
    }
  }

  // private def createMethodMenuEntries(sorting: Sorting[_, _]) = {
  //   val count = new AtomicInteger()
  //   retrieveMethodNames(sorting)
  //     .map(
  //       mName => MenuEntry(count.incrementAndGet(), mName, () => executeSortMethod(sorting, mName))
  //     )
  //     .toList
  // }

  // private def retrieveMethodNames(sorting: Sorting[_, _]) =
  //   sorting.getClass.getMethods.map(_.getName).filter(_.endsWith("Sort")).distinct

  // private def executeSortMethod(sorting: Sorting[_, _], method: String) = {
  //   println("You've chosen " + method + "! Please enter a list of comma separated integers.")
  //   val numberInputs = readNumberInputs
  //   println(
  //     s"You entered:${numberInputs.mkString(",")}. They are going to be sorted by $method.\n Sorting..."
  //   )
  //   val sorted = execute(sorting, method, numberInputs)
  //   println(s"Your number entries sorted are: ${sorted.mkString(",")}")
  // }

  // private def execute[F[_], T](sorting: Sorting[_, _], method: String, numberInputs: F[_]) = {
  //   findMethod(sorting, method) match {
  //     case Some(m: Method) => m.invoke(sorting, numberInputs).asInstanceOf[F[_]]
  //     case None            => throw new RuntimeException(s"Method $method not found in $sorting")
  //   }
  // }

  // private def findMethod(sorting: Sorting[_, _], method: String) =
  //   sorting.getClass.getMethods.find(m => m.getName.compare(method) == 0)
}
