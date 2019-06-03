import sqlite3
from Object import Object

conn = sqlite3.connect('mollys_mansion(5).db')
c = conn.cursor()
gameComplete = False

c.execute("select count(*) from object")
rows = c.fetchone()[0]

first = [0] * rows


#start of game
def start():
    reset()
    c.execute("select desc from object where id = 0")
    print(c.fetchone()[0])
    c.execute("select first_time_desc from object where name = 'player'")
    print(c.fetchone()[0])
    c.execute("select holder from object where id = 1")
    holder = c.fetchone()[0]
    c.execute("select name from object where id = :get", {'get': holder})
    print("Location: ", c.fetchone()[0])
    c.execute("select first_time_desc from object where id = :loc", {'loc': holder})
    print("Descripition: ", c.fetchone()[0])
    global first
    first[2] = 1
    

#North
def getN(id):
    c.execute("select N from object where id = :number", {'number': id})
    output = c.fetchone()[0]
    return output

#South
def getS(id):
    c.execute("select S from object where id = :number", {'number': id})
    output = c.fetchone()[0]
    return output

#East
def getE(id):
    c.execute("select E from object where id = :number", {'number': id})
    output = c.fetchone()[0]
    return output

#West
def getW(id):
    c.execute("select W from object where id = :number", {'number': id})
    output = c.fetchone()[0]
    return output

#Up
def getU(id):
    c.execute("select U from object where id = :number", {'number': id})
    output = c.fetchone()[0]
    return output

#Down
def getD(id):
    c.execute("select D from object where id = :number", {'number': id})
    output = c.fetchone()[0]
    return output

#look
def getDesc(id):
    global first
    if first[id] == 0:
        c.execute("select first_time_desc from object where id = :loc", {'loc': id})
        desc = c.fetchone()[0]
        first[id] = 1
        if desc == None:
            c.execute("select desc from object where id = :loc", {'loc': id})
        else:
            c.execute("select first_time_desc from object where id = :loc", {'loc': id})
    else:
        c.execute("select desc from object where id = :loc", {'loc': id})
    
    return c.fetchone()[0]

#move
def move(temploc, location):
    c.execute("select name from object where id = :loc", {'loc': temploc})
    temp = c.fetchone()
    if temp is not None:
        c.execute("select name from object where id = :loc", {'loc': temploc})
        temp = c.fetchone()[0]
        print("Location: ",temp)
        print(getDesc(temploc))
        c.execute("update object set holder = :loc where name = 'player'", {'loc': temploc})
        return temploc
    else:
        print("Can't go that way")
        return location


def reset():
    c.execute("update object set holder = 2 where name = 'player'")
    c.execute("update object set holder = NULL where id = 2")
    c.execute("update object set holder = NULL where id = 3")
    c.execute("update object set holder = NULL where id = 4")
    c.execute("update object set holder = NULL where id = 5")
    c.execute("update object set holder = NULL where id = 6")
    c.execute("update object set holder = NULL where id = 7")
    c.execute("update object set holder = NULL where id = 8")
    c.execute("update object set holder = NULL where id = 9")
    c.execute("update object set holder = NULL where id = 10")
    c.execute("update object set holder = NULL where id = 11")
    c.execute("update object set holder = NULL where id = 12")
    c.execute("update object set holder = NULL where id = 13")
    c.execute("update object set holder = 14 where id = 15")
    c.execute("update object set holder = 2 where id = 16")
    c.execute("update object set holder = 4 where id = 17")
    c.execute("update object set holder = 8 where id = 18")
    c.execute("update object set holder = 8 where id = 19")
    c.execute("update object set holder = 19 where id = 20")
    c.execute("update object set holder = 8 where id = 21")
    c.execute("update object set holder = 8 where id = 22")
    c.execute("update object set holder = 5 where id = 23")

#Start of game
start()

location = 2

while gameComplete == False:
    retrieve = input("Enter input: ")

    RetrieveList = retrieve.split(' ')

    c.execute("select verb_id from verbs where verb = :ret", {'ret': RetrieveList[0].lower()} )

    answer = c.fetchone()[0]

    #commands

    #Get
    if answer == 0:
        if len(RetrieveList) > 1:
            c.execute("select is_getable from object where name = :ret", {'ret': RetrieveList[1]})
            if c.fetchone()[0] == 1:
                c.execute("select holder from object where name = :ret", {'ret': RetrieveList[1].lower()})
                hold = c.fetchone()[0]
                if hold == location:
                    c.execute("update object set holder = 14 where name = :ret", {'ret': RetrieveList[1]})
                    print("Picked up: ", RetrieveList[1])
                else:
                    print("Item not here")
            else:
                print("Can't pick that up")
        else:
            print("Can't pick that up")
    #Look
    elif answer == 1:
        print(getDesc(location))
    #examine
    elif answer == 2:
        if len(RetrieveList) > 1:
            c.execute("select holder from object where name = :ret", {'ret': RetrieveList[1].lower()})
            ex = c.fetchone()[0]
            if ex == location or ex == 14 or ex == 1:
                c.execute("select id from object where name = :ret", {'ret': RetrieveList[1].lower()})
                item = c.fetchone()[0]
                print("Descripition: ",getDesc(item))
            else:
                print("Can't examine that")
        else:
            print("Can't examine that")
    #Drop
    elif answer == 3: 
        if len(RetrieveList) > 1:
            c.execute("select holder from object where name = :ret", {'ret': RetrieveList[1].lower()})
            hold = c.fetchone()[0]
            if hold == 14:
                c.execute("update object set holder = {} where name = '{}'".format(location, RetrieveList[1]))
                print("Dropped up: ", RetrieveList[1])
            else:
                print("Item not here")
        else:
            print("Can't drop that")
    elif answer == 4:
        temploc = getN(location)
        location = move(temploc, location)
    elif answer == 5:
        temploc = getE(location)
        location = move(temploc, location)
    elif answer == 6:
        temploc = getW(location)
        location = move(temploc, location)
    elif answer == 7:
        temploc = getS(location)
        location = move(temploc, location)
    elif answer == 8:
        temploc = getU(location)
        location = move(temploc, location)
    elif answer == 9:
        temploc = getD(location)
        location = move(temploc, location)
    else:
        print("Not a valid input")  

    

        


c.execute("drop object")

conn.close()
print("Hello World")