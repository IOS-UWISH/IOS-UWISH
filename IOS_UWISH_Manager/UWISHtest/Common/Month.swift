//
//  Month.swift
//  UWISHtest
//
//  Created by 洪郁茹 on 2020/6/7.
//  Copyright © 2020 Nancy. All rights reserved.
//

import Foundation

class Month: Codable{
    internal init(Jan: Double? = nil, Feb: Double? = nil, Mar: Double? = nil, Apr: Double? = nil, May: Double? = nil, Jun: Double? = nil, Jly: Double? = nil, Aug: Double? = nil, Sep: Double? = nil, Oct: Double? = nil, Nov: Double? = nil, Dec: Double? = nil) {
        self.Jan = Jan
        self.Feb = Feb
        self.Mar = Mar
        self.Apr = Apr
        self.May = May
        self.Jun = Jun
        self.Jly = Jly
        self.Aug = Aug
        self.Sep = Sep
        self.Oct = Oct
        self.Nov = Nov
        self.Dec = Dec
    }
    
    var Jan: Double?
    var Feb: Double?
    var Mar: Double?
    var Apr: Double?
    var May: Double?
    var Jun: Double?
    var Jly: Double?
    var Aug: Double?
    var Sep: Double?
    var Oct: Double?
    var Nov: Double?
    var Dec: Double?
    
    
    public func info()->String{
        return "Jan: \(Jan!),Feb: \(Feb!),Mar: \(Mar!),Apr: \(Apr!),May: \(May!),Jun: \(Jun!),Jly: \(Jly!),Aug: \(Aug!),Sep: \(Sep!),Oct: \(Oct!),Nov: \(Nov!),Dec: \(Dec!)"
    }
    
    public static func info(_ months:[Month]) -> String{
        var text = ""
        for month in months{
            text += month.info() + "\n"
        }
        return text
    }
    
}

