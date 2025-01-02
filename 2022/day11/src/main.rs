use std::fs;

fn part2(data: &str) -> u64 {
    let mut iter = data.lines();
    let mut monkeys: Vec<Vec<u64>> = Vec::new();
    let mut funcs: Vec<Box<dyn Fn(u64) -> u64>> = Vec::new();
    let mut divs: Vec<u64> = Vec::new();
    let mut ifs: Vec<(u64, u64)> = Vec::new();
    loop {
        iter.next();
        let line = &iter.next().unwrap()[18..];
        monkeys.push(line.split(", ").map(|i| i.parse().unwrap()).collect());
        let line = iter.next().unwrap();
        funcs.push(Box::new(|i: u64| {
            if &line[23..] == "* old" {
                i * i
            } else if line.chars().nth(23).unwrap() == '+' {
                i + &line[25..].parse().unwrap()
            } else {
                i * &line[25..].parse().unwrap()
            }
        }));
        divs.push(iter.next().unwrap()[21..].parse().unwrap());
        ifs.push((
            (iter.next().unwrap().chars().last().unwrap() as u64) - ('0' as u64),
            (iter.next().unwrap().chars().last().unwrap() as u64) - ('0' as u64),
        ));
        if iter.next().is_none() {
            break;
        }
    }
    let modulo = divs.iter().copied().reduce(|a, b| a * b).unwrap();
    let mut result = vec![0; monkeys.len()];
    for _ in 0..10000 {
        for i in 0..monkeys.len() {
            while !monkeys[i].is_empty() {
                let cur = funcs[i](monkeys[i].remove(0)) % modulo;
                monkeys[if cur % divs[i] == 0 {
                    ifs[i].0
                } else {
                    ifs[i].1
                } as usize]
                    .push(cur);
                result[i] += 1;
            }
        }
    }
    result.sort();
    println!("{:?}", result);
    result[result.len() - 2..]
        .iter()
        .copied()
        .reduce(|a, b| a * b)
        .unwrap()
}

fn part1(data: &str) -> u32 {
    let mut iter = data.lines();
    let mut monkeys: Vec<Vec<u32>> = Vec::new();
    let mut funcs: Vec<Box<dyn Fn(u32) -> u32>> = Vec::new();
    let mut divs: Vec<u32> = Vec::new();
    let mut ifs: Vec<(u32, u32)> = Vec::new();
    loop {
        iter.next();
        let line = &iter.next().unwrap()[18..];
        monkeys.push(line.split(", ").map(|i| i.parse().unwrap()).collect());
        let line = iter.next().unwrap();
        funcs.push(Box::new(|i: u32| {
            if &line[23..] == "* old" {
                i * i
            } else if line.chars().nth(23).unwrap() == '+' {
                i + &line[25..].parse().unwrap()
            } else {
                i * &line[25..].parse().unwrap()
            }
        }));
        divs.push(iter.next().unwrap()[21..].parse().unwrap());
        ifs.push((
            (iter.next().unwrap().chars().last().unwrap() as u32) - ('0' as u32),
            (iter.next().unwrap().chars().last().unwrap() as u32) - ('0' as u32),
        ));
        if iter.next().is_none() {
            break;
        }
    }
    let mut result = vec![0; monkeys.len()];
    for _ in 0..20 {
        for i in 0..monkeys.len() {
            while !monkeys[i].is_empty() {
                let cur = funcs[i](monkeys[i].remove(0)) / 3;
                monkeys[if cur % divs[i] == 0 {
                    ifs[i].0
                } else {
                    ifs[i].1
                } as usize]
                    .push(cur);
                result[i] += 1;
            }
        }
    }
    result.sort();
    result[result.len() - 2..]
        .iter()
        .copied()
        .reduce(|a, b| a * b)
        .unwrap()
}

fn main() {
    let data = fs::read_to_string("day11.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
