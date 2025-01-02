use std::fs;

#[derive(PartialEq, Clone)]
enum Bot {
    Ore,
    Clay,
    Obsidian,
    Geode,
}

#[derive(Clone)]
struct State {
    ore: u32,
    clay: u32,
    obsidian: u32,
    geode: u32,
    ore_bots: u32,
    clay_bots: u32,
    obsidian_bots: u32,
    geode_bots: u32,
    time: u32,
    next_bot: Bot,
}

fn optim(line: &str, limit: u32) -> u32 {
    let split: Vec<&str> = line.split_whitespace().collect();
    let ore_cost: u32 = split[6].parse().unwrap();
    let clay_cost: u32 = split[12].parse().unwrap();
    let obsidian_cost: (u32, u32) = (split[18].parse().unwrap(), split[21].parse().unwrap());
    let geode_cost: (u32, u32) = (split[27].parse().unwrap(), split[30].parse().unwrap());
    let init = State {
        ore: 0,
        clay: 0,
        obsidian: 0,
        geode: 0,
        ore_bots: 1,
        clay_bots: 0,
        obsidian_bots: 0,
        geode_bots: 0,
        time: 0,
        next_bot: Bot::Geode,
    };
    let mut stack: Vec<State> = vec![
        State { ..init },
        State {
            next_bot: Bot::Obsidian,
            ..init
        },
        State {
            next_bot: Bot::Clay,
            ..init
        },
        State {
            next_bot: Bot::Ore,
            ..init
        },
    ];
    let mut max = 0;
    while !stack.is_empty() {
        let mut cur = stack.pop().unwrap();
        if cur.geode + cur.geode_bots * (limit - cur.time) > max {
            max = cur.geode + cur.geode_bots * (limit - cur.time);
        }
        if cur.time == limit
            || (cur.next_bot == Bot::Obsidian && cur.obsidian_bots >= geode_cost.1)
            || (cur.next_bot == Bot::Clay && cur.clay_bots >= obsidian_cost.1)
            || (cur.next_bot == Bot::Ore
                && cur.ore_bots
                    >= *[ore_cost, clay_cost, obsidian_cost.0, geode_cost.0]
                        .iter()
                        .max()
                        .unwrap())
            || cur.geode
                + cur.geode_bots * (limit - cur.time)
                + (limit - 1 - cur.time) * (limit - cur.time) / 2
                <= max
        {
            continue;
        }
        let mut to_build = (0, 0, 0, 0);
        if cur.next_bot == Bot::Geode && cur.ore >= geode_cost.0 && cur.obsidian >= geode_cost.1 {
            cur.ore -= geode_cost.0;
            cur.obsidian -= geode_cost.1;
            to_build.0 += 1;
        } else if cur.next_bot == Bot::Obsidian
            && cur.ore >= obsidian_cost.0
            && cur.clay >= obsidian_cost.1
        {
            cur.ore -= obsidian_cost.0;
            cur.clay -= obsidian_cost.1;
            to_build.1 += 1;
        } else if cur.next_bot == Bot::Clay && cur.ore >= clay_cost {
            cur.ore -= clay_cost;
            to_build.2 += 1;
        } else if cur.next_bot == Bot::Ore && cur.ore >= ore_cost {
            cur.ore -= ore_cost;
            to_build.3 += 1;
        }
        cur.geode += cur.geode_bots;
        cur.obsidian += cur.obsidian_bots;
        cur.clay += cur.clay_bots;
        cur.ore += cur.ore_bots;
        cur.geode_bots += to_build.0;
        cur.obsidian_bots += to_build.1;
        cur.clay_bots += to_build.2;
        cur.ore_bots += to_build.3;
        if to_build == (0, 0, 0, 0) {
            stack.push(State {
                time: cur.time + 1,
                ..cur
            });
        } else {
            if cur.ore_bots > 0 {
                if cur.obsidian_bots > 0 {
                    stack.push(State {
                        next_bot: Bot::Geode,
                        time: cur.time + 1,
                        ..cur
                    });
                }
                if cur.clay_bots > 0 {
                    stack.push(State {
                        next_bot: Bot::Obsidian,
                        time: cur.time + 1,
                        ..cur
                    });
                }
                stack.push(State {
                    next_bot: Bot::Clay,
                    time: cur.time + 1,
                    ..cur
                });
            }
            stack.push(State {
                next_bot: Bot::Ore,
                time: cur.time + 1,
                ..cur
            });
        }
    }
    max
}

fn part2(data: &str) -> u32 {
    let mut result = 1;
    let mut iter = data.lines();
    for _ in 0..3 {
        result *= optim(iter.next().unwrap(), 32);
    }
    result
}

fn part1(data: &str) -> u32 {
    let mut result = 0;
    let mut idx = 1;
    for line in data.lines() {
        result += optim(line, 24) * idx;
        idx += 1;
    }
    result
}

fn main() {
    let data = fs::read_to_string("day19.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
