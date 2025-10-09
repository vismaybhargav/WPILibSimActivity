# FSMBotTemplate

Finite-state-machine based template project for WPILib based robot code.

To provide a more structured framework for FIRST Robotics Competition robot development, this project defines subsystem behaviors strictly in terms of multiple separate finite state machines updated in a round-robin fashion. This will make scheduling behavior explicitly visible instead of hidden behind the command scheduler, and avoid ambiguous shared state between command and subsystems under the WPILib command based programming model.

## FSMSystem
The primary base class for robot systems. Each robot system is defined in terms of a Mealy-style finite state machine with control over a well-defined set of robot hardware. 

## TeleopInput
Utility class with ownership of teleop input handling. The single global instance of this class mediates access to inputs during the teleoperated mode and abstracts control mappings.

Hi this is 2473's guide on how to use Simulation

First if you're not using a laptop with WPI already installed you need to download it.

    WPILib
        How to install WPILib
            Go to this [link](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)

            Scroll down and install the WPI Installer

                Once you have opened the installer you will see a welcome message, click Start in the bottom right

                For the install mode select everything and then install for this user

                Then you should select the option "Download for this computer only(fastest)" 

                Its about 2 GB so it might take a while.

        Using WPILib for Simulation

            First you need code to actually execute

            Git Set Up
            
                Git is how SW does version control or in other words how we log our code so we can go back to previous versions,
                you will learn a lot more if you are on SW

                If you already have git installed you can skip this step.

                If you are on a Mac, go to your terminal and type git version, the terminal will prompt you to download the needed tools and you should accept

                If you're on Windows you will need to the link below and follow the instructions to download git 

                    [link](https://git-scm.com/downloads/win)

            Cloning the Repo

                Go to this [link](https://github.com/vismaybhargav/WPILibSimActivity)
                Click the bright green button labled code, copy the link you see

                Once you have copied the link, go to your terminal and navigate to your Projects folder, if you dont have one make one

                once you are in projects folder, type "git clone" followed by the link

                This will create a folder in your Projects folder called WPILibSimActivity this is the code you're executing


    How to run Simulation

        Open WPILib and then open the WPILibSimActivity repository in WPI

        Now click the WPI logo in the top right and type Simulate Robot code