//
//  Day.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/7.
//  Copyright © 2020 Nancy. All rights reserved.
//

import Foundation

class Day: Codable{
    internal init(one: Double? = nil, two: Double? = nil, three: Double? = nil, four: Double? = nil, five: Double? = nil, six: Double? = nil, seven: Double? = nil, eight: Double? = nil, nine: Double? = nil, ten: Double? = nil, tenone: Double? = nil, tentwo: Double? = nil, tenthree: Double? = nil, tenfour: Double? = nil, tenfive: Double? = nil, tensix: Double? = nil, tenseven: Double? = nil, teneight: Double? = nil, tennine: Double? = nil, twenty: Double? = nil, twentyone: Double? = nil, twentytwo: Double? = nil, twentythree: Double? = nil, twentyfour: Double? = nil, twentyfive: Double? = nil, twentysix: Double? = nil, twentyseven: Double? = nil, twentyeight: Double? = nil, twentynine: Double? = nil, thirty: Double? = nil, thirtyone: Double? = nil) {
        self.one = one
        self.two = two
        self.three = three
        self.four = four
        self.five = five
        self.six = six
        self.seven = seven
        self.eight = eight
        self.nine = nine
        self.ten = ten
        self.tenone = tenone
        self.tentwo = tentwo
        self.tenthree = tenthree
        self.tenfour = tenfour
        self.tenfive = tenfive
        self.tensix = tensix
        self.tenseven = tenseven
        self.teneight = teneight
        self.tennine = tennine
        self.twenty = twenty
        self.twentyone = twentyone
        self.twentytwo = twentytwo
        self.twentythree = twentythree
        self.twentyfour = twentyfour
        self.twentyfive = twentyfive
        self.twentysix = twentysix
        self.twentyseven = twentyseven
        self.twentyeight = twentyeight
        self.twentynine = twentynine
        self.thirty = thirty
        self.thirtyone = thirtyone
    }
    
    var one : Double?
    var two : Double?
    var three : Double?
    var four : Double?
    var five : Double?
    var six : Double?
    var seven : Double?
    var eight : Double?
    var nine : Double?
    var ten : Double?
    var tenone : Double?
    var tentwo : Double?
    var tenthree : Double?
    var tenfour : Double?
    var tenfive : Double?
    var tensix : Double?
    var tenseven : Double?
    var teneight : Double?
    var tennine : Double?
    var twenty : Double?
    var twentyone : Double?
    var twentytwo : Double?
    var twentythree : Double?
    var twentyfour :Double?
    var twentyfive : Double?
    var twentysix : Double?
    var twentyseven : Double?
    var twentyeight : Double?
    var twentynine : Double?
    var thirty : Double?
    var thirtyone : Double?
    
    public func info() -> String {
        return "one: \(one!),two: \(two!), three: \(three!), four: \(four!),five: \(five!),six: \(six!),seven: \(seven!),eight: \(eight!),nine: \(nine!),ten: \(ten!),tenone: \(tenone!),tentwo: \(tentwo!),tenthree: \(tenthree!),tenfour: \(tenfour!),tenfive: \(tenfive!),tensix: \(tensix!),tenseven: \(tenseven!),teneight: \(teneight!),tennine: \(tennine!),twenty: \(twenty!),twentyone: \(twentyone!),twentytwo: \(twentytwo!),twentythree: \(twentythree!),twentyfour: \(twentyfour!),twentyfive: \(twentyfive!),twentysix: \(twentysix!),twentyseven: \(twentyseven!),twentyeight: \(twentyeight!),twentynine: \(twentynine!),thirty: \(thirty!),thirtyone: \(thirtyone!)"
    }
    
    public static func info(_ days:[Day]) -> String {
        var text = ""
        for day in days{
            text += day.info() + "\n"
        }
        return text
    }
    
}

